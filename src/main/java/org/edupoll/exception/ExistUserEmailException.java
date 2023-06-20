package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExistUserEmailException extends Exception{

	public ExistUserEmailException(String message) {
		super(message);
	}
	
}
