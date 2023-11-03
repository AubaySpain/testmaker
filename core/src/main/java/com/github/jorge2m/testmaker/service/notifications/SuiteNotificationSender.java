package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;

public interface SuiteNotificationSender {

	public boolean canSend(SuiteTM suite);
	public void send(SuiteTM suite);
	
	public static SuiteNotificationSender make() {
		return new TeamsSuiteNotification();
	}
}
