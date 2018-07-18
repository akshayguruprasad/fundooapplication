package com.indream.fundoo.configuration;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.indream.fundoo.exceptionhandler.AynchExceptionHandler;

@Configuration
@EnableAsync
public class AsyncCustomExecutor implements AsyncConfigurer {

	
	@Bean(name="threadpoolexec")
	public Executor getExecutor() {

		return new ThreadPoolTaskExecutor();
	}

	@Override
	public Executor getAsyncExecutor() {
		return getExecutor();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AynchExceptionHandler();
	}

}