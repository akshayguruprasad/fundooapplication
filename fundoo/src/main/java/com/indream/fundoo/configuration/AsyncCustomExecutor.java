package com.indream.fundoo.configuration;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.indream.fundoo.exceptionhandler.AynchExceptionHandler;

/**
 * CONFIGURATION FOR THE ASYNC CONFIGURER
 * 
 * @author Akshay
 *
 */
@Configuration
@EnableAsync
public class AsyncCustomExecutor implements AsyncConfigurer {

    /*
     * @purpose EXECUTOR BEAN CREATED [THREADPOOLTASKEXECUTOR]
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Bean(name = "threadpoolexec")
    public Executor getExecutor() {

	return new ThreadPoolTaskExecutor();// NEW OPERATOR
    }

    /*
     * @purpose OVERRIDE MEHTOD TO GET THE CUSTOM BEAN FOR EXECUTOR
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public Executor getAsyncExecutor() {
	return getExecutor();
    }

    /*
     * @purpose ASYNC EXCEPTION HANDLER IMPLEMENTATION PROVIDED
     *
     * @author akshay
     * 
     * @com.indream.fundoo.configuration
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
	return new AynchExceptionHandler();
    }

}