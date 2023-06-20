package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotExistUserException extends Exception {
	public NotExistUserException(String message) {
		super(message);
	}
}
