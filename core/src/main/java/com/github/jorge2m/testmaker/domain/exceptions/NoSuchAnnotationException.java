package com.github.jorge2m.testmaker.domain.exceptions;

public class NoSuchAnnotationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchAnnotationException(Exception e) {
        super("Teams Notification could not be sent: " + e);
    }
	
	public NoSuchAnnotationException(String message) {
        super(message);
    }
	
}
