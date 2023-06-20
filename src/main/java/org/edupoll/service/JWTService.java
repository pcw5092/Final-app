package org.edupoll.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JWTService {

	@Value("${jwt.secret.key}")
	String secretKey;
	
	public String createToken(String email) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		
		return JWT.create()
			.withIssuer("finalApp")
			// 서명시간
			.withIssuedAt(new Date(System.currentTimeMillis()))
			// 만료시간
			.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
			.withClaim("email", email)
			.withClaim("rank", 3)
			.sign(algorithm);
		
	}

	// token 유효성 검사

	public String verifyToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		var verifier =JWT.require(algorithm).withIssuer("finalApp").build();
		DecodedJWT decodedJWT =  verifier.verify(token);
		log.info(decodedJWT.getIssuer());
		log.info(decodedJWT.getClaim("email").asString());
		log.info(decodedJWT.getClaim("rank").asInt().toString());
		
		return decodedJWT.getClaim("email").asString();
	}
}
