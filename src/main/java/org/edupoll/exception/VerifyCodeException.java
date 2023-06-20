package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerifyCodeException extends Exception {
	
	public VerifyCodeException(String message) {
		super(message);
	}
}
