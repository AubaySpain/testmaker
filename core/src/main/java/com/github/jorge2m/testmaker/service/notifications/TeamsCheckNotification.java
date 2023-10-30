package com.github.jorge2m.testmaker.service.notifications;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;

public class TeamsCheckNotification extends TeamsNotificationBase implements CheckAlarmSender {

	@Override
	public void send(Check check, ChecksTM parentChecks) throws UnsendNotification {
		sendToTeams(
				DataAlert.of(check, parentChecks),
				getTeamsChanelURL(parentChecks.getSuiteParent()));
	}
	
}
