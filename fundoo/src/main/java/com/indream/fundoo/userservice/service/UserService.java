package com.indream.fundoo.userservice.service;

import com.indream.fundoo.userservice.model.UserDto;

public interface UserService {

	void registerUser(UserDto user);

	void activateUser(String token);

	String loginUser(UserDto user);

	void resetUserPassword(String id);

	void updatePassword(String token, UserDto user);

	void deleteUser(UserDto userDto);

}
