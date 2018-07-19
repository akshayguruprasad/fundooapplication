package com.indream.fundoo.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.indream.fundoo.userservice.model.UserResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<UserResponse> getException(UserException e) {

		UserResponse response = new UserResponse(e.getMessage(), 5001);
		return new ResponseEntity<UserResponse>(response, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(value = TokenException.class)
	public ResponseEntity<UserResponse> getException(TokenException e) {

		UserResponse response = new UserResponse(e.getMessage(), 6001);
		return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<UserResponse> getException(Exception e) {

		UserResponse response = new UserResponse(e.getMessage(), 7001);
		return new ResponseEntity<UserResponse>(response, HttpStatus.NOT_ACCEPTABLE);

	}

	@ExceptionHandler(value = GenericException.class)
	public ResponseEntity<UserResponse> getException(GenericException e) {

		UserResponse response = new UserResponse(e.getMessage(), 1001);
		return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);

	}
}
