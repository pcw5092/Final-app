package org.edupoll.config;

import org.edupoll.exception.ExistUserEmailException;
import org.edupoll.exception.InvalidPasswordException;
import org.edupoll.exception.JWTDecodedException;
import org.edupoll.exception.NotExistUserException;
import org.edupoll.exception.VerifyCodeException;
import org.edupoll.model.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;

@ControllerAdvice
public class ExceptionHandlerConfiguration {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(MethodArgumentNotValidException ex) {
		
		ErrorResponse error = new ErrorResponse(400, "오류1", System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(ExistUserEmailException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(ExistUserEmailException ex) {

		ErrorResponse error = new ErrorResponse(400, "사용중인 계정입니다.", System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(InvalidPasswordException ex) {

		ErrorResponse error = new ErrorResponse(401, "비밀번호가 일치하지 않습니다.", System.currentTimeMillis());
		// 권한없음 상태메시지
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(NotExistUserException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(NotExistUserException ex) {

		ErrorResponse error = new ErrorResponse(400,"이메일이 일치하지 않습니다.", System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({JWTDecodedException.class, TokenExpiredException.class})
	public ResponseEntity<ErrorResponse> JWTexceptionHandle(Exception ex) {

		ErrorResponse error = 
				new ErrorResponse(401, "인증코드가 만료되었거나, 손상되었습니다.", System.currentTimeMillis());
		
		// 권한없음 상태메시지
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(VerifyCodeException.class)
	public ResponseEntity<ErrorResponse> exceptionHandle(VerifyCodeException ex) {
		ErrorResponse response = new ErrorResponse(400, ex.getMessage(), System.currentTimeMillis());
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}






