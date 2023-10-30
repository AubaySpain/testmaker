package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;

public interface SuiteNotificationSender {

	public boolean canSend(SuiteTM suite);
	public void send(SuiteTM suite) throws UnsendNotification;
	
	public static SuiteNotificationSender make() {
		return new TeamsSuiteNotification();
	}
}
