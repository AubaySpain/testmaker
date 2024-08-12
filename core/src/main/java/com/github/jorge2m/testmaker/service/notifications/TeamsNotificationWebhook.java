package com.github.jorge2m.testmaker.service.notifications;

import org.json.simple.JSONValue;

public class TeamsNotificationWebhook extends TeamsNotification {

	public static boolean isUrlChannelOk(String url) {
		return url.contains("webhook");
	}
	
	@Override
    public String getBodyMessage(DataAlert dataAlert) { 
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
		message.append("        \"activitySubtitle\": \"On suite " + JSONValue.escape(dataAlert.getSuiteName() + " (" + dataAlert.getChannel() + " - " + dataAlert.getApp() + " - " + dataAlert.getUrlBase()) + ") execution\",\r\n");
		message.append("        \"activityImage\": \"" + ICON_TESTMAKER + "\",\r\n");
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

}
