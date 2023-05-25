package com.github.jorge2m.testmaker.service.exceptions;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(Exception e) {
        super("Element not found: " + e);
    }
	
	public NotFoundException(String message) {
        super(message);
    }
	
}
