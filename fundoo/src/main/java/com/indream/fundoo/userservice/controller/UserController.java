package com.indream.fundoo.userservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.indream.fundoo.userservice.dto.UserEntityDTO;
import com.indream.fundoo.userservice.model.UserResponse;
import com.indream.fundoo.userservice.service.UserService;

@RestController
@RequestMapping("/user-application")
public class UserController {
	@Autowired
	private UserService service;

	final Logger LOG = Logger.getLogger(UserController.class);

	@RequestMapping(path = "/registeration", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> userRegisteration(@RequestBody UserEntityDTO userDto) {
		service.registerUser(userDto);
		return new ResponseEntity<UserResponse>(
				new UserResponse("Registeration success activation link has be sent to registered email", 1),
				HttpStatus.OK);
	}

	@RequestMapping(path = "/activate/account", method = RequestMethod.GET)
	public ResponseEntity<UserResponse> activateAccount(HttpServletRequest request) {
		String token = request.getHeader("authorization");
		service.activateUser(token);
		return new ResponseEntity<UserResponse>(new UserResponse("Account activate success", 2), HttpStatus.OK);

	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> userLogin(@RequestBody UserEntityDTO user) {
		String tokenData = service.loginUser(user);
		return new ResponseEntity<UserResponse>(new UserResponse(tokenData, 3), HttpStatus.OK);
	}

	@RequestMapping(path = "/reset/password/{email:.+}", method = RequestMethod.PUT)
	public ResponseEntity<UserResponse> forgotPassword(@PathVariable("email") String email) {
		service.resetUserPassword(email);
		return new ResponseEntity<UserResponse>(new UserResponse("Success in sending the reset mail", 4),
				HttpStatus.OK);

	}

	@RequestMapping(path = "/update/password", method = RequestMethod.PUT)

	public ResponseEntity<UserResponse> updatePassword(@RequestBody UserEntityDTO userDto, HttpServletRequest request) {
		String token = request.getHeader("authorization");
		service.updatePassword(token, userDto);
		return new ResponseEntity<UserResponse>(new UserResponse("Password updated success", 5), HttpStatus.OK);
	}

	@RequestMapping(path = "/delete/user", method = RequestMethod.DELETE)

	public ResponseEntity<UserResponse> deleteUser(@RequestBody UserEntityDTO userDto) {
		service.deleteUser(userDto);
		return new ResponseEntity<UserResponse>(new UserResponse("User deleted= success", 6), HttpStatus.OK);
	}
	
	
}
