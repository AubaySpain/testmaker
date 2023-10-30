package com.github.jorge2m.testmaker.domain;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.notifications.CheckAlarmSender;
import com.github.jorge2m.testmaker.service.notifications.DataAlert;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;

public class Alarm {

	private static final int DEFAULT_PERIOD = 60;
	
	private final Check check;
	private final ChecksTM checksParent;
	private final InputParamsTM inputParams;
	private final CheckAlarmSender alarmSender = CheckAlarmSender.instance();
	private final RepositoryI repository = TestMaker.getRepository();
	
	public Alarm(Check check, ChecksTM checksParent) {
		this.check = check;
		this.checksParent = checksParent;
		this.inputParams = checksParent.getSuiteParent().getInputParams();
	}
	
	public void send() {
		if (!isMaxAlarmsReached()) {
			sendWithExceptionControl();
			store();
		}
	}
	
	private void sendWithExceptionControl() {
		try {
			alarmSender.send(check, checksParent);
		} 
		catch (UnsendNotification e) {
			Log4jTM.getLogger().error("Problem sending notification", e);
		}		
	}
	
	private void store() {
		repository.storeAlert(check, checksParent);
	}
	
    private boolean isMaxAlarmsReached() {
    	if (inputParams.getMaxAlarms()==null) {
    		return false;
    	}
    	
    	int periodAlarms = getPeriodAlarms();
    	List<DataAlert> alarmsInPeriod = repository.getAlertsInPeriod(periodAlarms, check, checksParent);
    	int maxAlarms = Integer.valueOf(inputParams.getMaxAlarms());
    	
    	return (alarmsInPeriod.size() >= maxAlarms);   
    }	
	
    private int getPeriodAlarms() {
    	if (inputParams.getMaxAlarms()==null) {
    		return DEFAULT_PERIOD;
    	}
    	return Integer.valueOf(inputParams.getPeriodAlarms());
    }
}
