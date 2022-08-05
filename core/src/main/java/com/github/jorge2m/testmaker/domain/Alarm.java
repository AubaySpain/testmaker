package com.github.jorge2m.testmaker.domain;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.notifications.AlarmSender;

public class Alarm {

	private final AlarmSender alarmSender;
	private final Check check;
	private final ChecksTM checksParent;
	
	public Alarm(Check check, ChecksTM checksParent) {
		this.alarmSender = AlarmSender.instance();
		this.check = check;
		this.checksParent = checksParent;
	}
	
	public void send() {
		alarmSender.send(check, checksParent);
		store();
	}
	
	private void store() {
		RepositoryI repository = TestMaker.getRepository();
		repository.storeAlert(check, checksParent);
	}
	
}
