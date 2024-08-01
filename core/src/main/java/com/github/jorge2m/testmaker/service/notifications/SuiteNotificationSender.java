package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;

public interface SuiteNotificationSender {

	public boolean canSend();
	public void send();
	
	public static SuiteNotificationSender make(SuiteTM suite) {
		return new TeamsSuiteNotification(suite);
	}
}
