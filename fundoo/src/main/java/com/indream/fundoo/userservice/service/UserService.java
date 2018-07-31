package com.indream.fundoo.userservice.service;

import com.indream.fundoo.noteservice.model.Token;
import com.indream.fundoo.userservice.model.UserDto;

public interface UserService {

	void registerUser(UserDto user);

	void activateUser(Token token);

	String loginUser(UserDto user);

	void resetUserPassword(String id);

	void updatePassword(Token token, UserDto user);

	public void deleteUser(UserDto userDto,Token token) ;

}
