package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerifyPasswordException extends Exception {
	public VerifyPasswordException(String message) {
		super(message);
	}
}
