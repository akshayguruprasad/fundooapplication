package com.indream.fundoo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.indream.fundoo.exceptionhandler.TokenException;
import com.indream.fundoo.userservice.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenManagerImpl implements TokenManager {
	final Logger LOG = Logger.getLogger(TokenManagerImpl.class);

	@Autowired
	Environment env;

	@Override
	public String generateToken(UserEntity requester) {
		LOG.info("Enter [TokenManagerImpl][generateToken]");
		LOG.info("method param : " + requester);
		String token = null;
		Map<String, Object> claims = null;
		GregorianCalendar calendar = null;
		Date date = null;
		date = new Date();
		calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 10);
		claims = new HashMap<String, Object>();
		claims.put("name", requester.getUserName());
		claims.put("id", requester.getId());
		JwtBuilder jwtbuilder = Jwts.builder().setClaims(claims);
		jwtbuilder.setExpiration(calendar.getTime());
		jwtbuilder.setIssuedAt(date);
		jwtbuilder.setIssuer(requester.getEmail());
		jwtbuilder.signWith(SignatureAlgorithm.HS256, env.getProperty("secretkey"));
		token = jwtbuilder.compact();
		LOG.info("Response  " + token);
		LOG.info("Exit [TokenManagerImpl][generateToken]");
		return token;
	}

	@Override
	public Claims validateToken(String token) {
		LOG.info("Enter [TokenManagerImpl][validateToken]");
		LOG.info("method param : " + token);

		try {
			Jws<Claims> jwtClaims = Jwts.parser().setSigningKey(env.getProperty("secretkey")).parseClaimsJws(token);
			Claims claims = jwtClaims.getBody();

			Date expirationDate = claims.getExpiration();
			if (expirationDate.compareTo(new Date()) > -1) {
				throw new TokenException("Token has expired");
			}

			return claims;
		} catch (TokenException e) {
			throw e;
		}

	}

}
