package com.indream.fundoo.util;

import com.indream.fundoo.userservice.model.UserEntity;

import io.jsonwebtoken.Claims;

public interface TokenManager {

	String generateToken(UserEntity requester);

	Claims validateToken(String token);

}
