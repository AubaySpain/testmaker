package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;

public interface AlarmSender {
	
	public static AlarmSender instance() {
		return new TeamsNotification();
	}
	
	public void send(Check check, ChecksTM parentChecks) throws UnsendNotification;
}
