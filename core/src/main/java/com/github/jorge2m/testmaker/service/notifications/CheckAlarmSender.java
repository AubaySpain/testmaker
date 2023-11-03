package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public interface CheckAlarmSender {
	
	public static CheckAlarmSender instance() {
		return new TeamsCheckNotification();
	}
	
	public void send(Check check, ChecksTM parentChecks);
}
