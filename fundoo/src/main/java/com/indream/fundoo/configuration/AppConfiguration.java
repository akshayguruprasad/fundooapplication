package com.indream.fundoo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indream.fundoo.noteservice.service.NoteService;
import com.indream.fundoo.noteservice.service.NoteServiceImpl;
import com.indream.fundoo.userservice.service.UserServiceImpl;
import com.indream.fundoo.util.MessageService;
import com.indream.fundoo.util.SpringMailingServiceImpl;
import com.indream.fundoo.util.TokenManagerImpl;

@Configuration
@PropertySource(value = { "classpath:application.properties", "classpath:ErrorProperties.properties",
		"classpath:LiteralProperties.properties", "classpath:mail.properties", "classpath:credentials.properties" })
public class AppConfiguration {

	@Bean(name = "service")
	public UserServiceImpl getServiceImpl() {
		return new UserServiceImpl();
	}

	@Bean(name = "manager")
	public TokenManagerImpl getTokenManagerImpl() {
		return new TokenManagerImpl();
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(name = "springMessage")
	public MessageService getSpringBean() {

		return new SpringMailingServiceImpl();
	}

	@Bean(name = "noteService")
	public NoteService getNoteService() {

		return new NoteServiceImpl();
	}

	@Bean(name = "jacksonMapper")
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.IGNORE_UNKNOWN, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;

	}

	@Bean(name = "mapper")
	public ModelMapper getModelMapper() {

		return new ModelMapper();

	}
}
