package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidPasswordException extends Exception{
	public InvalidPasswordException(String message) {
		super(message);
	}
}
