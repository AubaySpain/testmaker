package com.github.jorge2m.testmaker.testreports.html;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;

public class DynatraceLinks {

	private static final String PATH_MULTIDIMENSIONAL_ANALYSIS = "/ui/apps/dynatrace.classic.mda/ui/diagnostictools/mda?";
	private static final String PATH_DISTRIBUTED_TRACES = "/ui/apps/dynatrace.classic.distributed.traces/ui/diagnostictools/purepaths?";
	private static final String FILTER_ROBOTEST_EXECUTION = "0%1E15%11744d0067-137d-4acc-8b0f-e6093512721c%14";
	private static final String FILTER_ROBOTEST_TESTCASE = "%1015%11a5c87655-79bb-4c0c-bd0b-1348dddb5820%14";
	
	private final String dynatraceSubdomain;
	private final String idExecSuite;
	private final SuiteTM suite; 
	
	public DynatraceLinks(SuiteTM suite) {
		this.dynatraceSubdomain = suite.getInputParams().getDynatracesd();
		this.idExecSuite = suite.getIdExecution();
		this.suite = suite;
	}
	
	public String getMultidimensionalAnalisisForSuiteURL() {
		return 
			"https://" + dynatraceSubdomain + PATH_MULTIDIMENSIONAL_ANALYSIS + 
			"metric=FAILURE_RATE&" + 
			"mergeServices=true&" + 
			"gtf=" + getDynatraceGftValue(suite.getInicio(), suite.getFin()) + "&" +
			"servicefilter=" + FILTER_ROBOTEST_EXECUTION + idExecSuite;
	}
	
	public String getDistributedTracesForTestCaseURL(TestCaseBean testCase) {
		return
			"https://" + dynatraceSubdomain + PATH_DISTRIBUTED_TRACES + 
			"gtf=" + getDynatraceGftValue(testCase.getInicioDate(), testCase.getFinDate()) + "&" +
			"servicefilter=" + 
			FILTER_ROBOTEST_EXECUTION + idExecSuite + 
			FILTER_ROBOTEST_TESTCASE + testCase.getCode();
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
        	suite.getLogger().fatal("Problem formatting data for url dynatrace generation", e);
        }

        return start + "+to+" + end;
	}
	
    private Pair<Date, Date> adjustToDynatraceInterval(Date startDateIni, Date endDateIni) {
        Date startDate = startDateIni;
        Date endDate = getRoundDateToSequentMinute(endDateIni);

        long differenceInMillis = endDate.getTime() - startDateIni.getTime();
        long thirtyMinutesInMillis = 30 * 60 * 1000;

        if (differenceInMillis < thirtyMinutesInMillis) {
            long additionalMillis = (thirtyMinutesInMillis - differenceInMillis);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDateIni);
            calendar.add(Calendar.MILLISECOND, (int) -additionalMillis);
            startDate = calendar.getTime();
        }

        return Pair.of(startDate, endDate);
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
