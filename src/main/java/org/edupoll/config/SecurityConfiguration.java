package org.edupoll.config;

import org.edupoll.config.support.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

	@Autowired
	JWTAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	SecurityFilterChain finalAppSecurityChain(HttpSecurity http) throws Exception {

		http.csrf(t -> t.disable()); // REST 에서는 필요가 없다.
		http.sessionManagement(t -> t //
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션생성전략

		// 경로에 따른 인증이 필요한가 아닌가 설정 하는 작업
		http.authorizeHttpRequests(t -> t //
				.requestMatchers("/api/v1/user/private/**").authenticated() //
				.requestMatchers(HttpMethod.POST, "/api/v1/feed/storage/**").authenticated() //
				.anyRequest().permitAll());

		http.anonymous(t -> t.disable()); // 익명인증을 사용 안한다.
		http.logout(t -> t.disable()); // 로그아웃 기능 없애기

		// AuthorizationFilter 필터 전에 jwtAuthenticationFilter 가 먼저 돌아가게 된다.
		http.addFilterBefore(jwtAuthenticationFilter, AuthorizationFilter.class);
		http.cors(withDefaults()); // cors 활성화
		return http.build();

	}

}
