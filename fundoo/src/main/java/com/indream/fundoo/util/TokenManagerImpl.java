package com.indream.fundoo.util;

import java.util.Date;
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

    /*
     * @purpose USE THOIS METHOD FOR GENERATION OF THE TOKENS
     *
     * @author akshay
     * 
     * @com.indream.fundoo.util
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public String generateToken(UserEntity requester) {
	LOG.info("Enter [TokenManagerImpl][generateToken]");
	LOG.info("method param : " + requester);
	String token = null;
	Map<String, Object> claims = null;
//	GregorianCalendar calendar = null;
	Date date = null;
	date = new Date();
//	calendar = new GregorianCalendar();
//	calendar.setTime(date);
//	calendar.add(Calendar.MINUTE, 10);
	claims = new HashMap<String, Object>();
	claims.put("name", requester.getName()); // USER FIRST NAME
	claims.put("id", String.valueOf(requester.getId()));// USER UNIQUE ID
	JwtBuilder jwtbuilder = Jwts.builder().setClaims(claims);
	jwtbuilder.setIssuedAt(date);// ISSUED ON
	jwtbuilder.setIssuer(requester.getEmail());// ISSUER EMAIL ID
	jwtbuilder.signWith(SignatureAlgorithm.HS256, env.getProperty("secretkey"));
	token = jwtbuilder.compact();// BUILD TOKEN
	LOG.info("Response  " + token);
	LOG.info("Exit [TokenManagerImpl][generateToken]");
	return token;
    }

    /*
     * @PURPOSE VALIDATE THE TOKENS
     *
     * @author akshay
     * 
     * @com.indream.fundoo.util
     * 
     * @since Jul 24, 2018
     *
     */
    @Override
    public Claims validateToken(String token) {
	LOG.info("Enter [TokenManagerImpl][validateToken]");
	LOG.info("method param : " + token);

	try {
	    Jws<Claims> jwtClaims = Jwts.parser().setSigningKey(env.getProperty("secretkey")).parseClaimsJws(token);
	    Claims claims = jwtClaims.getBody();// GET THE CLAIMS FROM THE TOEK WHICH IS PARSED
	    return claims;
	} catch (TokenException e) {
	    e.printStackTrace();
	    throw e;
	}

    }

}
