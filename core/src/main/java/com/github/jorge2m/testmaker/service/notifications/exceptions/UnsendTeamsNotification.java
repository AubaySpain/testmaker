package com.github.jorge2m.testmaker.service.notifications.exceptions;

public class UnsendTeamsNotification extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsendTeamsNotification(Exception e) {
        super("Teams Notification could not be sent: " + e);
    }
	
	public UnsendTeamsNotification(String message) {
        super(message);
    }
	
}
