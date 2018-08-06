package com.indream.fundoo.userservice.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.indream.fundoo.configuration.RabbitMqConfig;
import com.indream.fundoo.exceptionhandler.UserException;
import com.indream.fundoo.userservice.model.MailEntity;
import com.indream.fundoo.userservice.model.UserDto;
import com.indream.fundoo.userservice.model.UserEntity;
import com.indream.fundoo.userservice.repository.UserRepository;
import com.indream.fundoo.util.PreConditions;
import com.indream.fundoo.util.TokenManager;
import com.indream.fundoo.util.Utility;

/**
 * USER SERVICE IMPL MEHTOD FOR THE USER BUSINESS OPERATIONS
 * 
 * @author Akshay
 *
 */
//@PropertySource(value="")
public class UserServiceImpl implements UserService {
	final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private TokenManager manager;// TOKEN MANAGER IMPLEMENTATION

	@Autowired
	private PasswordEncoder passwordEncoder;// PASSWORD ENCODER BLOWFISH BLOCK CIPHER

	@Autowired
	private Environment env;// ENVIRMONENT FOR PROPERTIES TO BE READ

	@Autowired
	private UserRepository repository;// USER MONGO REPOSITORY

	@Autowired
	AmqpTemplate template;// AMQP TEMPLATE

	/*
	 * @purpose REGISTER THE USER INTO THE SYSTEM
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.userservice.service
	 * 
	 * @since Jul 24, 2018
	 *
	 */
	@Override
	public void registerUser(UserDto user) {
		LOG.info("Enter [UserServiceImpl][registerUser]");
		UserEntity userEntity = null;

		userEntity = repository.getByEmail(user.getEmail());
		PreConditions.checkNotNull(userEntity, env.getProperty("user.already.exists.error.message"),
				UserException.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(false);// SAVE THE USER IN THE BASE
		userEntity = Utility.convert(user, UserEntity.class);
		userEntity = repository.save(userEntity);
		String token = manager.generateToken(userEntity); // GENERATE AND BIND THE TOKEN TO URL
		String message = env.getProperty("user.activation.link.prefix") + token
				+ env.getProperty("user.activation.link.suffix");
		MailEntity mail = new MailEntity();
		mail.setSubject(env.getProperty("user.activation.email.subject"));
		mail.setMessage(message);
		mail.setTo(user.getEmail());
		String mailString = Utility.covertToJSONString(mail);
		template.convertAndSend(RabbitMqConfig.TOPICEXCHANGENAME, RabbitMqConfig.ROUTING_KEY, mailString);

	}

	/*
	 * @purpose ACTIVATE THE USER BASED ON THE VALID TOKEN THAT IS PASSED
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.userservice.service
	 * 
	 * @since Jul 24, 2018
	 *
	 */
	@Override
	public void activateUser(String token) {
		LOG.info("Enter [UserServiceImpl][activateUser]");
		UserEntity user = repository.findOne(token);// GET THE USER VALUE
		user.setActive(true);// UPDATE THE USER VALUE TO TRUE
		repository.save(user);// SAVE THE USER VALUE
		LOG.info("Exit [UserServiceImpl][activateUser]");
	}

	/*
	 * @purpose LOGINUSER METHOD WILL GENERATE A TOKEN FOR THE USER AND SEND AS
	 * RESPONSE
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.userservice.service
	 * 
	 * @since Jul 24, 2018
	 *
	 */
	@Override
	public String loginUser(UserDto user) {
		LOG.info("Enter [UserServiceImpl][loginUser]");
		UserEntity userEntity = null;
		String token = null;
		userEntity = repository.getByEmail(user.getEmail());// GET USER BY EMAIL ID
		PreConditions.checkNull(userEntity, env.getProperty("user.find.error.message"), UserException.class);
		PreConditions.checkFalse(userEntity.isActive(), env.getProperty("user.activation.false"), UserException.class);
		if (!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {// CHECK FOR USERPASSWORD AND
			throw new UserException(env.getProperty("user.password.mismatch.error.message"));
		}
		token = manager.generateToken(userEntity);// IF VALID LOGIN THEN PROVIDE THE USER WITH APPROPRIATE TOKEN

		return token;
	}

	/*
	 * @purpose RESET THE USER PASSWORD
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.userservice.service
	 * 
	 * @since Jul 24, 2018
	 *
	 */
	@Override
	public void resetUserPassword(String emailId) {
		LOG.info("Enter [UserServiceImpl][resetUserPassword]");
		UserEntity user = null;
		String newPassword = null;
		user = repository.getByEmail(emailId);// GET THE USER ENTITY BY THE EMAIL ID
		PreConditions.checkNull(user, env.getProperty("user.find.error.message"), UserException.class);
		newPassword = passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10));// GENERATE AN ALPHANUMBER
		user.setPassword(newPassword);// SET THE NEW PASSWORD
		user = repository.save(user);// UPDATE THE USER
		String token = manager.generateToken(user);// GENERATE A TOKEN FOR THE USER
		MailEntity mail = new MailEntity();// CREATE A MAIL ENTITY
		mail.setSubject(env.getProperty("user.activation.email.subject"));// SET THE APPROPRIATE VALUE FOR THE
		mail.setMessage(env.getProperty("user.reset.link.link") + token);
		mail.setTo(user.getEmail());
		String mailString = Utility.covertToJSONString(mail);// CONVERT IT TO JSON
		template.convertAndSend(RabbitMqConfig.TOPICEXCHANGENAME, RabbitMqConfig.ROUTING_KEY, mailString);// PRODUCE
		LOG.info("Exit [UserServiceImpl][resetUserPassword]");
	}

	/*
	 * @purpose UPDATE THE PASSWORD
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.userservice.service
	 * 
	 * @since Jul 24, 2018
	 *
	 */
	@Override
	public void updatePassword(String token, UserDto userDto) {
		PreConditions.checkNotMatch(userDto.getPassword(), userDto.getConfirmPassword(),
				env.getProperty("user.password.mismatch.error.message"), UserException.class);

		PreConditions.checkNotMatch(userDto.getEmail(), repository.findOne(token).getEmail(),
				env.getProperty("user.email.mismatch.error.message"), UserException.class);
		UserEntity user = repository.getByEmail(userDto.getEmail());// GET THE USER DEATISL BY THE EMAIL
		user.setPassword(passwordEncoder.encode(userDto.getConfirmPassword()));// ENCODE THE PASSWORD
		repository.save(user);// UPDATE THE PASSWORD
	}

	/*
	 * @purpose DELETE THE USER FROM THE SYSTEM
	 *
	 * @author akshay
	 * 
	 * @com.indream.fundoo.userservice.service
	 * 
	 * @since Jul 24, 2018
	 *
	 */
	@Override
	public void deleteUser(String token) {

		UserEntity userValue = repository.findOne(token);// GET THE USER BY THE USER ID
		UserEntity user = repository.getByEmail(userValue.getEmail());// GET THE USER BY THE USER EMAIL ENTERED
		PreConditions.checkNotMatch(user, userValue, env.getProperty("user.failed.mismatch.error.message"),
				UserException.class);
		repository.delete(token);// A VALID LOGIN AND SO DELETE THE USER
	}
}
