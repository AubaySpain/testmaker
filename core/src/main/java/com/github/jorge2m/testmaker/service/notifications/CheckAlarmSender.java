package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;

public interface CheckAlarmSender {
	
	public static CheckAlarmSender instance(SuiteTM suite) {
		return new TeamsCheckNotification(suite);
	}
	
	public void send(Check check, ChecksTM parentChecks);
}
