package com.bnp.bookstore.exceptions;

public class ResourceNotFoundException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
