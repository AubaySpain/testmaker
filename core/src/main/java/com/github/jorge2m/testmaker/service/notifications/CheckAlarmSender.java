package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;

public interface CheckAlarmSender {
	
	public static CheckAlarmSender instance() {
		return new TeamsCheckNotification();
	}
	
	public void send(Check check, ChecksTM parentChecks) throws UnsendNotification;
}
