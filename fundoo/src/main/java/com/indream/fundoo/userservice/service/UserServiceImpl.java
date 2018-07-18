package com.indream.fundoo.userservice.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.indream.fundoo.exceptionhandler.UserException;
import com.indream.fundoo.userservice.dto.UserEntityDTO;
import com.indream.fundoo.userservice.model.UserEntity;
import com.indream.fundoo.userservice.repository.UserRepository;
import com.indream.fundoo.util.MessageService;
import com.indream.fundoo.util.TokenManager;

import io.jsonwebtoken.Claims;

public class UserServiceImpl implements UserService {
	final Logger LOG = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private Environment env;

	@Autowired
	private MessageService springMessage;

	@Autowired
	private TokenManager manager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public void registerUser(UserEntityDTO user) {
		LOG.info("Enter [UserServiceImpl][registerUser]");
		LOG.info("Method param user :  " + user);

		UserEntity userEntity = null;
		try {
			userEntity = repository.getByEmail(user.getEmail());
			if (userEntity != null) {
				// THROW EXCEPTION AS REGISTERATION ALREADY EXISTS
				throw new UserException(env.getProperty("user.already.exists.error.message"));
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setActive(false);// SAVE THE USER IN THE BASE
			userEntity = mapper.map(user, UserEntity.class);
			userEntity = repository.save(userEntity);
			String token = manager.generateToken(userEntity); // GENERATE AND BIND THE TOKEN TO URL
			String message = env.getProperty("user.activation.link.prefix") + token
					+ env.getProperty("user.activation.link.suffix");
			springMessage.sendMessage(user.getEmail(), env.getProperty("user.activation.email.subject"), message);
		} catch (UserException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Exception occured [UserServiceImpl][registerUser] " + e.getMessage());
			e.printStackTrace();
		}
		LOG.info("Exit [UserServiceImpl][registerUser]");
	}

	@Override
	public void activateUser(String token) {
		LOG.info("Enter [UserServiceImpl][activateUser]");
		LOG.info("Method param token : " + token);
		Claims claims = null;
		try {
			claims = manager.validateToken(token);
			String id = claims.get("id").toString();
			UserEntity user = repository.findOne(id);
			user.setActive(true);
			repository.save(user);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Exception occured [UserServiceImpl][activateUser] " + e.getMessage());
		}
		LOG.info("Exit [UserServiceImpl][activateUser]");

	}

	@Override
	public String loginUser(UserEntityDTO user) {
		LOG.info("Enter [UserServiceImpl][loginUser]");
		LOG.info("Method param " + user);
		UserEntity userEntity = null;
		String token = null;
		try {
			userEntity = repository.getByEmail(user.getEmail());
			if (userEntity == null) {
				throw new UserException(env.getProperty("user.find.error.message"));
			}
			if (!userEntity.isActive()) {
				throw new UserException(env.getProperty("user.activation.false"));
			}
			if (!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
				throw new UserException(env.getProperty("user.password.mismatch.error.message"));
			}
			token = manager.generateToken(userEntity);
		} catch (UserException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Exception occured [UserServiceImpl][loginUser] " + e.getMessage());
		}

		return token;
	}

	@Override
	public void resetUserPassword(String emailId) {
		LOG.info("Enter [UserServiceImpl][resetUserPassword]");
		LOG.info("Method param " + emailId);
		UserEntity user = null;
		String newPassword = null;
		try {
			user = repository.getByEmail(emailId);
			if (user == null) {
				throw new UserException(env.getProperty("user.find.error.message"));
			}
			newPassword = passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(10));
			user.setPassword(newPassword);
			user = repository.save(user);
			String token = manager.generateToken(user);
			springMessage.sendMessage(user.getEmail(), env.getProperty("user.reset.email.subject"),
					env.getProperty("user.reset.link.link") + token);
			System.out.println("------------this will print before the thread finishes its execution");
		} catch (UserException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Exception occured [UserServiceImpl][resetUserPassword] " + e.getMessage());
		}
		LOG.info("Exit [UserServiceImpl][resetUserPassword]");
	}

	@Override
	public void updatePassword(String token, UserEntityDTO userDto) {
		try {
			if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
				throw new UserException(env.getProperty("user.password.mismatch.error.message"));
			}
			UserEntity user = repository.getByEmail(userDto.getEmail());
			user.setPassword(passwordEncoder.encode(userDto.getConfirmPassword()));
			repository.save(user);
		} catch (UserException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			LOG.error("Exception in [UserServiceImpl][updatePassword] " + e.getMessage());
		}

	}

	@Override
	public void deleteUser(UserEntityDTO userDto) {

		try {

			String token = this.loginUser(userDto);
			Claims claims = manager.validateToken(token);
			String id = claims.get("id").toString();

			repository.delete(id);

		} catch (UserException e) {
			throw e;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}
