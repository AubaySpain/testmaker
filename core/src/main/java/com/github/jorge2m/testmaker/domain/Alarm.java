package com.github.jorge2m.testmaker.domain;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.notifications.AlarmSender;

public class Alarm {

	private final AlarmSender alarmSender;
	
	public Alarm() {
		this.alarmSender = AlarmSender.instance();
	}
	
	public void send(Check check, ChecksTM checksParent) {
		alarmSender.send(check, checksParent);
	}
	
}
