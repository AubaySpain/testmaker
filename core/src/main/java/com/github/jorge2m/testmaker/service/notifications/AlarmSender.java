package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.Check;

public interface AlarmSender {
	
	public static AlarmSender instance() {
		return new TeamsNotification();
	}
	
	public void send(Check check);
}
