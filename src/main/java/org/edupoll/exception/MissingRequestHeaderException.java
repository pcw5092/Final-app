package org.edupoll.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MissingRequestHeaderException extends Exception{
	
	public MissingRequestHeaderException(String message) {
		super(message);
	}
}	
