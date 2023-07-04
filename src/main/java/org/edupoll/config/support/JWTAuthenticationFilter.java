package org.edupoll.config.support;

import java.io.IOException;
import java.util.ArrayList;

import org.edupoll.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
 * Custom 인증방식
 */
@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");
		log.info("Authorization header value = " + authorization);

		// JWT 유효성 검사
		if (authorization == null) {
			// 사용자가 JWT 토큰을 안가지고 왔다면 통과
			log.info("Did not process authentication request since failed authorization header", authorization);
			filterChain.doFilter(request, response);
			return;
		}
		// 사용자가 JWT 토큰을 가지고 왔다면
		try {
			String email = jwtService.verifyToken(authorization); // 토큰 소유자의 정보가 나온다.
			// 여기까지 왔다면 통과 했다.
			Authentication authentication = 				/* ↓ : 인증추최자 정보 */
					new UsernamePasswordAuthenticationToken(email, authorization , new ArrayList<>());
					/* 1. principal ==> 인증주체자 : UserDetails 객체가 보통 설정된다.
					 * 	  	@AuthenticationPrincipal 했을 때 나오는 값이다.
					 * 2. credential ==> 인증에 사용됐던 정보 (크게 상관없다.)
					 * 3. authorities ==> 권한 : role 에 대한 판단.
					 */
			// 인증통과 상태로 만든다.
			// log.info("!! !! !! authentication = {}", authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			// 토큰이 만료되었거나, 위조된 상황
			log.warn("Verify token fail = ", e.getMessage());
			throw new BadCredentialsException("Invalid authentication token");
		}

		// 자격증명 정보 추출하는 방법 (인증정보)
		// JWT 유효성 검사해서 통과 하면 ↘

		filterChain.doFilter(request, response);
	}

}
