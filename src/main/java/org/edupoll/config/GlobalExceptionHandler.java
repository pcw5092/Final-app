package org.edupoll.config;

import org.edupoll.exception.ExistUserEmailException;
import org.edupoll.exception.InvalidPasswordException;
import org.edupoll.exception.NotExistUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Void> exceptionHandle(MethodArgumentNotValidException ex) {
		
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(ExistUserEmailException.class)
	public ResponseEntity<Void> exceptionHandle(ExistUserEmailException ex) {

		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<Void> exceptionHandle(InvalidPasswordException ex) {

		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(NotExistUserException.class)
	public ResponseEntity<Void> exceptionHandle(NotExistUserException ex) {

		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
}






