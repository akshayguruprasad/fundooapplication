package com.indream.fundoo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indream.fundoo.interceptors.TokenValidatorInterceptor;
import com.indream.fundoo.noteservice.service.NoteService;
import com.indream.fundoo.noteservice.service.NoteServiceImpl;
import com.indream.fundoo.userservice.service.UserServiceImpl;
import com.indream.fundoo.util.MessageService;
import com.indream.fundoo.util.SpringMailingServiceImpl;
import com.indream.fundoo.util.TokenManagerImpl;

/**
 * APPLICATION CONFIGURATION SPRING IOC
 * 
 * @author Akshay
 *
 */
@Configuration
@PropertySource(value = { "classpath:application.properties", "classpath:ErrorProperties.properties",
	"classpath:LiteralProperties.properties", "classpath:mail.properties", "classpath:credentials.properties" })
public class AppConfiguration extends WebMvcConfigurerAdapter {

    /*
     * @purpose CREATES A BEAN FOR THE USER SERVICE LAYER
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean(name = "service")
    public UserServiceImpl getServiceImpl() {
	return new UserServiceImpl();// NEW OPERATOR
    }

    /*
     * @purpose TOKEN MANAGER SINGLETON BEAN CREATED TO USE
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean(name = "manager")
    public TokenManagerImpl getTokenManagerImpl() {
	return new TokenManagerImpl();// NEW OPERATOR
    }

    /*
     * @purpose PASSWORDENCODER BEAN CREATED TO ENCODE THE PASSWORD SPRING SECURITY
     * CRYPTO
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();// NEW OPERATOR
    }

    /*
     * @purpose SPRING IMPLEMENTATION FOR THE JAVA MAIL BEAN CREATED TO USE THE JAVA
     * MAILING SERVICES
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean(name = "springMessage")
    public MessageService getSpringBean() {

	return new SpringMailingServiceImpl();// NEW OPERATOR
    }

    /*
     * @purpose NOTE SERVICE LAYER IMPLEMENTATION CREATED FOR USING BEAN CREATION
     * 
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean(name = "noteService")
    public NoteService getNoteService() {

	return new NoteServiceImpl();// NEW OPERATOR
    }

    /*
     * @purpose JACKSON MAPPER BEAN CREATED TO BE AUTOWIRED TO MAP OBJECTS
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean(name = "jacksonMapper")
    public ObjectMapper getObjectMapper() {
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(Feature.IGNORE_UNKNOWN, true);
	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	return mapper;

    }

    /*
     * @purpose MODEL MAPPER BEAN CREATED FOR MAPPING PROPERTIES FROM ENTITY TO DTO
     * v.v
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean(name = "mapper")
    public ModelMapper getModelMapper() {
	return new ModelMapper();// NEW OPERATOR

    }

    /*
     * @purpose APPLICATION SUPPORTS THE INTERCEPTORS AND ADDING TO REGISTERY
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
	interceptorRegistry.addInterceptor(getTokenInterceptor()).addPathPatterns("/noteapplication/*")
		.addPathPatterns("/userapplication/update/password").addPathPatterns("/userapplication/update/password")
		.addPathPatterns("/userapplication/delete/user").addPathPatterns("/userapplication/activate/account");

    }

    /*
     * @purpose CREATION OF INTERCEPTOR BEAN
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean
    public TokenValidatorInterceptor getTokenInterceptor() {
	return new TokenValidatorInterceptor();// NEW OPERATOR
    }

}// AppCOnfiguration class ends
