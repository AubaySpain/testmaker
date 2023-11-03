package com.github.jorge2m.testmaker.service.notifications;

import static org.apache.http.impl.client.HttpClients.createDefault;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONValue;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;
import com.github.jorge2m.testmaker.conf.Log4jTM;

public class TeamsNotificationBase {

	protected void sendToTeams(DataAlert dataAlert, String teamsUrl) {
        try (var httpClient = createDefault()) {
            var post = new HttpPost(teamsUrl);
            post.addHeader("Content-Type", "application/json");
            String bodyMessage = getBodyMessage(dataAlert);
            var entity = new StringEntity(bodyMessage, "UTF-8");
            post.setEntity(entity);
            var response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
            	String errorMessage = String.format(
            			"HttpError %s sending Teams Notification with body message %s", 
            			response.getStatusLine().getStatusCode(), bodyMessage);
                throw new UnsendNotification(errorMessage);
            }
        } catch (Exception e) {
        	Log4jTM.getLogger().error("Problem sending Teams Notification. {}. {}", e.getMessage(), e.getStackTrace());
        }
	}	
	
	protected String getTeamsChanelURL(SuiteTM suite) {
		return suite.getInputParams().getTeamsChannel();
	}
	
    protected String getBodyMessage(DataAlert dataAlert) { 
    	var bodyMessage = new StringBuilder();
    	bodyMessage.append("{\r\n");
    	bodyMessage.append(getCabecera());
    	bodyMessage.append(getSections(dataAlert));
    	bodyMessage.append(getPotentialActions(dataAlert));
    	bodyMessage.append("}");
    	return bodyMessage.toString();
    }
    
    private String getCabecera() {
    	return
    	  "    \"@type\": \"MessageCard\",\r\n" 
    	+ "    \"@context\": \"http://schema.org/extensions\",\r\n"
		+ "    \"themeColor\": \"0076D7\",\r\n"
		+ "    \"summary\": \"Suite with Defects\",\r\n";
    }
    	
    private String getSections(DataAlert dataAlert) {
    	var message = new StringBuilder();
    	
		message.append("    \"sections\": [{\r\n");
		message.append("        \"activityTitle\": \"TestMaker has detected problems\",\r\n");
		message.append("        \"activitySubtitle\": \"On suite " + JSONValue.escape(dataAlert.getSuiteName()) + " execution\",\r\n");
		message.append("        \"activityImage\": \"https://teamsnodesample.azurewebsites.net/static/img/image5.png\",\r\n");
		message.append("        \"facts\": [");
		if (!isVoid(dataAlert.getTestCaseName())) {
			message.append("{\r\n");
			message.append("            \"name\": \"Test Case\",\r\n");
			message.append("            \"value\": \"" + JSONValue.escape(dataAlert.getTestCaseName()) + "\"\r\n");
			message.append("        }, ");
		}
		if (!isVoid(dataAlert.getStepDescription())) {
			message.append("{\r\n");
			message.append("            \"name\": \"Step\",\r\n");
			message.append("            \"value\": \"" + JSONValue.escape(dataAlert.getStepDescription()) + "\"\r\n");
			message.append("        }, ");
		}
		if (!isVoid(dataAlert.getCheckDescription())) {
			message.append("{\r\n");
			message.append("            \"name\": \"Check\",\r\n");
			message.append("            \"value\": \"" + JSONValue.escape(dataAlert.getCheckDescription()) + "\"\r\n");
			message.append("        }, ");
		}
		if (!isVoid(dataAlert.getInfoExecution())) {
			message.append("{\r\n");
			message.append("            \"name\": \"Info\",\r\n");
			message.append("            \"value\": \"" + JSONValue.escape(dataAlert.getInfoExecution()) + "\"\r\n");
			message.append("        }");
		}
		message.append("],\r\n");
		message.append("        \"markdown\": true\r\n");
		message.append("    }],\r\n");
				
		return message.toString();
    }
		
    private String getPotentialActions(DataAlert dataAlert) {	
    	if (isVoid(dataAlert.getUrlReportSuite())) {
    		return "";
    	}
    	return 
    		"    \"potentialAction\": [{\r\n"
    	  + "        \"@type\": \"OpenUri\",\r\n"
		  + "        \"name\": \"Suite Report\",\r\n"
		  + "        \"targets\": [{\r\n"
		  + "            \"os\": \"default\",\r\n"
		  + "            \"uri\": \"" + JSONValue.escape(dataAlert.getUrlReportSuite()) + "\"\r\n"
		  + "        }]\r\n"
		  + "    }]\r\n";
    }
    
    protected boolean isVoid(String value) {
    	return value==null || "".compareTo(value)==0;
    }

}
