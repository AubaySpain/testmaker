package com.github.jorge2m.testmaker.service.notifications.exceptions;

public class UnsendNotification extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsendNotification(Exception e) {
        super("Notification could not be sent: " + e);
    }
	
	public UnsendNotification(String message) {
        super(message);
    }
	
}
