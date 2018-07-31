package com.indream.fundoo.userservice.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.indream.fundoo.configuration.RabbitMqConfig;
import com.indream.fundoo.exceptionhandler.UserException;
import com.indream.fundoo.noteservice.model.Token;
import com.indream.fundoo.userservice.model.MailEntity;
import com.indream.fundoo.userservice.model.UserDto;
import com.indream.fundoo.userservice.model.UserEntity;
import com.indream.fundoo.userservice.repository.UserRepository;
import com.indream.fundoo.util.TokenManager;
import com.indream.fundoo.util.Utility;

/**
 * USER SERVICE IMPL MEHTOD FOR THE USER BUSINESS OPERATIONS
 * 
 * @author Akshay
 *
 */
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
	try {
	    userEntity = repository.getByEmail(user.getEmail());
	    if (userEntity != null) {
		// THROW EXCEPTION AS REGISTERATION ALREADY EXISTS
		throw new UserException(env.getProperty("user.already.exists.error.message"));
	    }
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
	} catch (UserException e) {
	    LOG.error("Exception occured [UserServiceImpl][registerUser] " + e.getMessage());
	    throw e;
	} catch (RuntimeException e) {
	    LOG.error("Exception occured [UserServiceImpl][registerUser] " + e.getMessage());
	    throw e;
	} catch (Exception e) {
	    LOG.error("Exception occured [UserServiceImpl][registerUser] " + e.getMessage());
	}
	LOG.info("Exit [UserServiceImpl][registerUser]");
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
    public void activateUser(Token token) {
	LOG.info("Enter [UserServiceImpl][activateUser]");
	try {
	    String id = token.getId();// GET USER ID
	    UserEntity user = repository.findOne(id);// GET THE USER VALUE
	    user.setActive(true);// UPDATE THE USER VALUE TO TRUE
	    repository.save(user);// SAVE THE USER VALUE
	} catch (RuntimeException e) {
	    LOG.error("Exception occured [UserServiceImpl][activateUser] " + e.getMessage());
	    throw e;
	} catch (Exception e) {
	    LOG.error("Exception occured [UserServiceImpl][activateUser] " + e.getMessage());
	}
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
	try {
	    userEntity = repository.getByEmail(user.getEmail());// GET USER BY EMAIL ID
	    if (userEntity == null) {// IF NOT EXISTING THROW EXC
		throw new UserException(env.getProperty("user.find.error.message"));
	    }
	    if (!userEntity.isActive()) {// IF NOT ACTIVATED THE PROMPT THE USER TO ACTIVATE THE ACCOUNT
		throw new UserException(env.getProperty("user.activation.false"));
	    }
	    if (!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {// CHECK FOR USERPASSWORD AND
											 // REGISTERED USER PASSWORD
		throw new UserException(env.getProperty("user.password.mismatch.error.message"));
	    }
	    token = manager.generateToken(userEntity);// IF VALID LOGIN THEN PROVIDE THE USER WITH APPROPRIATE TOKEN
	} catch (UserException e) {
	    throw e;
	} catch (RuntimeException e) {
	    throw e;
	} catch (Exception e) {
	    LOG.error("Exception occured [UserServiceImpl][loginUser] " + e.getMessage());
	}

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
	try {
	    user = repository.getByEmail(emailId);// GET THE USER ENTITY BY THE EMAIL ID
	    if (user == null) {
		throw new UserException(env.getProperty("user.find.error.message"));
	    }
	    newPassword = passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10));// GENERATE AN ALPHANUMBER
											   // FOR LENGTH 10 FOR THE
											   // RESET
	    user.setPassword(newPassword);// SET THE NEW PASSWORD
	    user = repository.save(user);// UPDATE THE USER
	    String token = manager.generateToken(user);// GENERATE A TOKEN FOR THE USER
	    MailEntity mail = new MailEntity();// CREATE A MAIL ENTITY
	    mail.setSubject(env.getProperty("user.activation.email.subject"));// SET THE APPROPRIATE VALUE FOR THE
									      // MAILENTITY
	    mail.setMessage(env.getProperty("user.reset.link.link") + token);
	    mail.setTo(user.getEmail());
	    String mailString = Utility.covertToJSONString(mail);// CONVERT IT TO JSON
	    template.convertAndSend(RabbitMqConfig.TOPICEXCHANGENAME, RabbitMqConfig.ROUTING_KEY, mailString);// PRODUCE
													      // SENDS
													      // TO
													      // ECHANGE
	} catch (UserException e) {
	    LOG.error("Exception occured [UserServiceImpl][resetUserPassword] " + e.getMessage());
	    throw e;
	} catch (RuntimeException e) {
	    LOG.error("Exception occured [UserServiceImpl][resetUserPassword] " + e.getMessage());
	    throw e;
	} catch (Exception e) {
	    LOG.error("Exception occured [UserServiceImpl][resetUserPassword] " + e.getMessage());
	}
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
    public void updatePassword(Token token, UserDto userDto) {
	try {
	    if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {// COMPARE THE USER PASSWORD AND THE TOEKN
									      // PASSWORD
		throw new UserException(env.getProperty("user.password.mismatch.error.message"));
	    }
	    if (!userDto.getEmail().equals(token.getIssuer())) {// USER EMAIL AND THE TOKEN EMAIL
		throw new UserException(env.getProperty("user.email.mismatch.error.message"));

	    }
	    UserEntity user = repository.getByEmail(userDto.getEmail());// GET THE USER DEATISL BY THE EMAIL
	    user.setPassword(passwordEncoder.encode(userDto.getConfirmPassword()));// ENCODE THE PASSWORD
	    repository.save(user);// UPDATE THE PASSWORD
	} catch (UserException e) {
	    LOG.error("Exception in [UserServiceImpl][updatePassword] " + e.getMessage());
	    throw e;
	} catch (RuntimeException e) {
	    LOG.error("Exception in [UserServiceImpl][updatePassword] " + e.getMessage());
	    throw e;
	} catch (Exception e) {
	    LOG.error("Exception in [UserServiceImpl][updatePassword] " + e.getMessage());
	}

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
    public void deleteUser(UserDto userDto, Token token) {

	try {
	    String id = token.getId();// GET USER ID BY THE TOKEN
	    UserEntity userValue = repository.findOne(id);// GET THE USER BY THE USER ID
	    UserEntity user = repository.getByEmail(userValue.getEmail());// GET THE USER BY THE USER EMAIL ENTERED
	    if (!user.equals(userValue)) {
		throw new UserException(env.getProperty("user.failed.mismatch.error.message"));
	    }
	    repository.delete(id);// A VALID LOGIN AND SO DELETE THE USER
	} catch (UserException e) {
	    LOG.error("Exception in [UserServiceImpl][deleteUser] " + e.getMessage());
	    throw e;
	} catch (RuntimeException e) {
	    LOG.error("Exception in [UserServiceImpl][deleteUser] " + e.getMessage());
	    throw e;
	} catch (Exception e) {
	    LOG.error("Exception in [UserServiceImpl][deleteUser] " + e.getMessage());

	}

    }
}
