package com.indream.fundoo.userservice.service;

import com.indream.fundoo.userservice.dto.UserEntityDTO;

public interface UserService {

	void registerUser(UserEntityDTO user);

	void activateUser(String token);

	String loginUser(UserEntityDTO user);

	void resetUserPassword(String id);

	void updatePassword(String token, UserEntityDTO user);

	void deleteUser(UserEntityDTO userDto);

}
