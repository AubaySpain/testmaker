package com.github.jorge2m.testmaker.testreports.html;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.conf.ConfigLoader;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public class DynatraceLinks {

	private final boolean active; 
	private final String subdomain;
	private final String pathMultidimensionalAnalysis;
	private final String pathDistributedTraces;
	private final String filterRobotestExecution;
	private final String filterRobotestTestcase;

	private final String idExecSuite;
	private final SuiteBean suite; 
	
	public DynatraceLinks(SuiteBean suite) {
		var configLoader = new ConfigLoader();
		
		this.active = Boolean.parseBoolean(configLoader.getProperty("testmaker.dynatrace.active"));
		this.subdomain = configLoader.getProperty("testmaker.dynatrace.sd");
		this.pathMultidimensionalAnalysis = configLoader.getProperty("testmaker.dynatrace.multidimensional.path");
		this.pathDistributedTraces = configLoader.getProperty("testmaker.dynatrace.distributedtraces.path");
		this.filterRobotestExecution = configLoader.getProperty("testmaker.dynatrace.multidimensional.filter");
		this.filterRobotestTestcase = configLoader.getProperty("testmaker.dynatrace.distributedtraces.filter");
		
		this.idExecSuite = suite.getIdExecSuite();
		this.suite = suite;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String getMultidimensionalAnalisisForSuiteURL() {
		return 
			"https://" + subdomain + pathMultidimensionalAnalysis + 
			"metric=FAILURE_RATE&" + 
			"mergeServices=true&" + 
			"gtf=" + getDynatraceGftValue(suite.getInicioDate(), suite.getFinDate()) + "&" +
			"servicefilter=" + filterRobotestExecution + idExecSuite;
	}
	
	public String getDistributedTracesForTestCaseURL(TestCaseBean testCase) {
		return
			"https://" + subdomain + pathDistributedTraces + 
			"gtf=" + getDynatraceGftValue(testCase.getInicioDate(), testCase.getFinDate()) + "&" +
			"servicefilter=" + 
			filterRobotestExecution + idExecSuite + 
			filterRobotestTestcase + testCase.getNameUniqueNormalized();
	}
	
	private String getDynatraceGftValue(Date startDateI, Date endDateI) {
		var pair = adjustToDynatraceInterval(startDateI, endDateI);
		var startDate = pair.getLeft();
		var endDate = pair.getRight();
		
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        String start = dateFormat.format(startDate);
        String end = dateFormat.format(endDate);

        try {
            start = URLEncoder.encode(start, "UTF-8");
            end = URLEncoder.encode(end, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        	Log4jTM.getLogger().fatal("Problem formatting data for url dynatrace generation", e);
        }

        return start + "+to+" + end;
	}
	
    private Pair<Date, Date> adjustToDynatraceInterval(Date startDateI, Date endDateI) {
    	Date startDate = startDateI;
    	Date endDate;
    	if (!dateIsBeforeCurrentMinus1hour(endDateI)) {
    		endDate = getRoundDateToSequentMinute(endDateI);
    	} else {
    		endDate = new Date();
    	}

        long differenceInMillis = endDate.getTime() - startDateI.getTime();
        long twoHoursInMillis = 360 * 60 * 1000;

        if (differenceInMillis < twoHoursInMillis) {
            long additionalMillis = (twoHoursInMillis - differenceInMillis);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MILLISECOND, (int) -additionalMillis);
            startDate = calendar.getTime();
        }

        return Pair.of(startDate, endDate);
    }	
    
    private boolean dateIsBeforeCurrentMinus1hour(Date dateToCheck) {
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.HOUR_OF_DAY, -1);
        Date oneHourAgo = calendar.getTime();

        return dateToCheck.before(oneHourAgo);    	
    }
    
    private Date getRoundDateToSequentMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int seconds = calendar.get(Calendar.SECOND);
        int milliseconds = calendar.get(Calendar.MILLISECOND);
        if (seconds > 0 || milliseconds > 0) {
            calendar.add(Calendar.MINUTE, 1);
        }

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }

}
