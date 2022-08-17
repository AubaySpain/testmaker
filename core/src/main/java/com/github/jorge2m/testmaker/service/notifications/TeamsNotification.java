package com.github.jorge2m.testmaker.service.notifications;

import static org.apache.http.impl.client.HttpClients.createDefault;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONValue;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;


public class TeamsNotification implements AlarmSender {

	@Override
	public void send(Check check, ChecksTM parentChecks) throws UnsendNotification {
        try {
        	HttpClient httpClient = createDefault();
            HttpPost post = new HttpPost(getTeamsChanelURL(check, parentChecks));
            post.addHeader("Content-Type", "application/json");
            String bodyMessage = getBodyMessage(check, parentChecks);
            StringEntity entity = new StringEntity(bodyMessage, "UTF-8");
            post.setEntity(entity); 
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
            	String errorMessage = String.format(
            			"HttpError %s sending Teams Notification with body message %s", 
            			response.getStatusLine().getStatusCode(), bodyMessage);
                throw new UnsendNotification(errorMessage);
            }
        } catch (Exception e) {
            throw new UnsendNotification(e);
        }
	}
	
	private String getTeamsChanelURL(Check check, ChecksTM parentChecks) {
		InputParamsTM inputParams = parentChecks.getSuiteParent().getInputParams();
		return inputParams.getTeamsChannel();
	}
	
    private String getBodyMessage(Check check, ChecksTM checksParent) { 

    	DataAlert dataAlert = DataAlert.of(check, checksParent);
    	return "{\r\n"
		+ "    \"@type\": \"MessageCard\",\r\n"
		+ "    \"@context\": \"http://schema.org/extensions\",\r\n"
		+ "    \"themeColor\": \"0076D7\",\r\n"
		+ "    \"summary\": \"Validation KO\",\r\n"
		+ "    \"sections\": [{\r\n"
		+ "        \"activityTitle\": \"TestMaker has detected a problem\",\r\n"
		+ "        \"activitySubtitle\": \"On suite " + JSONValue.escape(dataAlert.getSuiteName()) + " execution\",\r\n"
		+ "        \"activityImage\": \"https://teamsnodesample.azurewebsites.net/static/img/image5.png\",\r\n"
		+ "        \"facts\": [{\r\n"
		+ "            \"name\": \"Test Case\",\r\n"
		+ "            \"value\": \"" + JSONValue.escape(dataAlert.getTestCaseName()) + "\"\r\n"
		+ "        }, {\r\n"
		+ "            \"name\": \"Step\",\r\n"
		+ "            \"value\": \"" + JSONValue.escape(dataAlert.getStepDescription()) + "\"\r\n"
		+ "        }, {\r\n"
		+ "            \"name\": \"Check\",\r\n"
		+ "            \"value\": \"" + JSONValue.escape(dataAlert.getCheckDescription()) + "\"\r\n"
		+ "        }, {\r\n"
		+ "            \"name\": \"Info\",\r\n"
		+ "            \"value\": \"" + JSONValue.escape(dataAlert.getInfoExecution()) + "\"\r\n"
		+ "        }],\r\n"		
		+ "        \"markdown\": true\r\n"
		+ "    }],\r\n"
		+ "    \"potentialAction\": [{\r\n"
		+ "        \"@type\": \"OpenUri\",\r\n"
		+ "        \"name\": \"Suite Report\",\r\n"
		+ "        \"targets\": [{\r\n"
		+ "            \"os\": \"default\",\r\n"
		+ "            \"uri\": \"" + JSONValue.escape(dataAlert.getUrlReportSuite()) + "\"\r\n"
		+ "        }]\r\n"
		+ "    }]\r\n"
		+ "}";	
    }

}
