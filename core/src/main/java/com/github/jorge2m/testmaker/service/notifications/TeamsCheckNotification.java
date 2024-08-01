package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;

public class TeamsCheckNotification implements CheckAlarmSender {

	private final TeamsNotification teamsNotification;
	
	public TeamsCheckNotification(SuiteTM suite) {
		this.teamsNotification = TeamsNotification.make(suite);
	}	
	
	@Override
	public void send(Check check, ChecksTM parentChecks) throws UnsendNotification {
		teamsNotification.sendToTeams(
				DataAlert.of(check, parentChecks),
				teamsNotification.getTeamsURL(parentChecks.getSuiteParent()));
	}
	
}
