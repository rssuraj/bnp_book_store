package com.bnp.bookstore.exceptions;

public class ResourceExistException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	public ResourceExistException(String message) {
        super(message);
    }
}
