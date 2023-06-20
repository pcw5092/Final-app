package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JWTDecodedException extends Exception {
	public JWTDecodedException(String message) {
		super(message);
	}
}
