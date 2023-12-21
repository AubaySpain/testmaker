package com.github.jorge2m.testmaker.service.notifications;

import static com.github.jorge2m.testmaker.conf.State.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;

public class TeamsSuiteNotification extends TeamsNotificationBase implements SuiteNotificationSender {

	@Override
	public boolean canSend(SuiteTM suite) {
		var inputParams = suite.getInputParams();
		return 
			!isVoid(inputParams.getTeamsChannel()) &&
			inputParams.isNotification();
	}
	
	@Override
	public void send(SuiteTM suite) throws UnsendNotification {
		sendToTeams(
				getDataAlert(suite),
				getTeamsChanelURL(suite));
	}	
	
    private DataAlert getDataAlert(SuiteTM suite) {
    	var dataCheckAlert = getDataCheckAlert(suite, DEFECT);
    	if (!dataCheckAlert.isEmpty()) {
    		return dataCheckAlert.get();
    	}
    	
   		dataCheckAlert = getDataCheckAlert(suite, KO);
   		if (!dataCheckAlert.isEmpty()) {
   			return dataCheckAlert.get();
   		}
   		return DataAlert.of(suite);
    }
    
    private Optional<DataAlert> getDataCheckAlert(SuiteTM suite, State state) {
		var listSteps = suite.getStepsFromNoRetriedTestCases(state);
		if (!listSteps.isEmpty()) {
			var firstStep = listSteps.get(0);
			var listChecks = getListChecks(firstStep, state);
			if (!listChecks.isEmpty()) {
				var checkNotOvercomed = getCheckNotOvercomed(listChecks);
				if (checkNotOvercomed.isPresent()) {
					return Optional.of(DataAlert.of(checkNotOvercomed.get(), firstStep));
				}
				return Optional.of(DataAlert.of(listChecks.get(0), firstStep));
			}
			if (state==KO) {
				return Optional.of(DataAlert.of(firstStep));
			}
		}
		return Optional.empty();
    }
    
    private Optional<Check> getCheckNotOvercomed(List<Check> listChecks) {
    	for (var check : listChecks) {
    		if (!check.isOvercomed()) {
    			return Optional.of(check);
    		}
    	}
    	return Optional.empty();
    }
	
    private List<Check> getListChecks(StepTM step, State state) {
    	return step.getListChecksTM().stream()
    		.filter(c -> c.getStateValidation()==state)
    		.map(c -> c.getListChecks())
    		.flatMap(List::stream)
    		.filter(c -> c.getLevelResult()==state)
    		.collect(Collectors.toList());
    }
    
}
