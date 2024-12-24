package com.github.jorge2m.testmaker.service.notifications;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.json.simple.JSONValue;

public class TeamsNotificationWorkflow extends TeamsNotification {

//	Configuración:
//		1) 3 puntos a la derecha del canal y selección de "Workflows"
//		2) Selecciona el Workflow “Publicar en un canal cuando se reciba una solicitud de webhook”:
//		3) Darle un nombre y pulsar Continuar
//		4) Copiar la URL asociada al flujo de trabajo y pulsar el botón "Listo"

	private static final String CRLF = "\r\n";
	
	public static boolean isUrlChannelOk(String url) {
		return url.contains("workflow");
	}
	
	@Override
	public String getBodyMessage(DataAlert dataAlert) { 
	    var bodyMessage = new StringBuilder();
	    bodyMessage.append(getInit());
	    bodyMessage.append(getBody(dataAlert));
	    bodyMessage.append(getActions(dataAlert));
	    bodyMessage.append(getFin());
	    return bodyMessage.toString();
	}

	private String getInit() {
	    var message = new StringBuilder();
	    message.append("{" + CRLF);
	    message.append("   \"attachments\": [" + CRLF); 
	    message.append("      {" + CRLF);
	    message.append("         \"contentType\": \"application/vnd.microsoft.card.adaptive\"," + CRLF);
	    message.append("         \"content\": {" + CRLF);  
	    message.append("            \"type\": \"AdaptiveCard\"," + CRLF);
	    message.append("            \"version\": \"1.0\"," + CRLF);
	    return message.toString();
	}

	private String getBody(DataAlert dataAlert) {
	    var message = new StringBuilder();
	    message.append("            \"body\": [" + CRLF);
	    message.append("               {" + CRLF);
	    message.append("                  \"type\": \"ColumnSet\"," + CRLF);
	    message.append("                  \"columns\": [" + CRLF);    	
	    message.append(getBodyImage());
	    message.append(getBodyContent(dataAlert));
	    message.append("                  ]" + CRLF);
	    message.append("               }" + CRLF);
	    message.append("            ]," + CRLF);
	    return message.toString();
	}

	private String getBodyImage() {
	    var message = new StringBuilder();
	    message.append("            {" + CRLF);
	    message.append("               \"type\": \"Column\"," + CRLF);
	    message.append("               \"width\": \"auto\"," + CRLF);
	    message.append("               \"items\": [" + CRLF);
	    message.append("                  {" + CRLF);
	    message.append("                     \"type\": \"Image\"," + CRLF);
	    message.append("                     \"url\": \"" + ICON_TESTMAKER + "\"," + CRLF);
	    message.append("                     \"size\": \"Medium\"," + CRLF);
	    message.append("                     \"altText\": \"TextMaker Image\"" + CRLF);
	    message.append("                  }" + CRLF);
	    message.append("              ]" + CRLF);
	    message.append("            }," + CRLF);    	
	    return message.toString();
	}

	private String getBodyContent(DataAlert dataAlert) {
	    var message = new StringBuilder();
	    message.append("            {" + CRLF);  // Se agregó un CRLF aquí para mantener el formato consistente
	    message.append("               \"type\": \"Column\"," + CRLF);
	    message.append("               \"width\": \"stretch\"," + CRLF);
	    message.append("               \"items\": [" + CRLF);
	    message.append("                  {" + CRLF);
	    message.append("                     \"type\": \"TextBlock\"," + CRLF);
	    message.append("                     \"size\": \"Medium\"," + CRLF);
	    message.append("                     \"weight\": \"Bolder\"," + CRLF);
	    message.append("                     \"text\": \"TestMaker has detected problems\"" + CRLF);
	    message.append("                  }," + CRLF);
	    message.append("                  {" + CRLF);
	    message.append("                     \"type\": \"TextBlock\"," + CRLF);
	    message.append("                     \"isSubtle\": true," + CRLF);
	    message.append("                     \"wrap\": true," + CRLF);
	    message.append("                     \"text\": \"On suite " + adjustText(dataAlert.getSuiteName() + " (" + dataAlert.getChannel() + " - " + dataAlert.getApp() + " - " + dataAlert.getUrlBase()) + " execution\"" + CRLF);
	    message.append("                  }," + CRLF);
	    message.append("                  {" + CRLF);
	    message.append("                     \"type\": \"FactSet\"," + CRLF);
	    message.append("                     \"facts\": [" + CRLF);
		if (!isVoid(dataAlert.getTestCaseName())) {
			message.append("                        {" + CRLF);
			message.append("                           \"title\": \"Test Case\"," + CRLF);
			message.append("                           \"value\": \"" + adjustText(dataAlert.getTestCaseName()) + "\"" + CRLF);
			message.append("                        }," + CRLF);
		}
		if (!isVoid(dataAlert.getStepDescription())) {
			message.append("                        {" + CRLF);
			message.append("                           \"title\": \"Step\"," + CRLF);
			message.append("                           \"value\": \"" + adjustText(dataAlert.getStepDescription()) + "\"" + CRLF);
			message.append("                        }," + CRLF);
		}
		if (!isVoid(dataAlert.getCheckDescription())) {
			message.append("                        {" + CRLF);
			message.append("                           \"title\": \"Check\"," + CRLF);
			message.append("                           \"value\": \"" + adjustText(dataAlert.getCheckDescription()) + "\"" + CRLF);
			message.append("                        }" + CRLF);
		}
		if (!isVoid(dataAlert.getInfoExecution())) {
			message.append("                        {" + CRLF);
			message.append("                           \"title\": \"Info\"," + CRLF);
			message.append("                           \"value\": \"" + adjustText(dataAlert.getInfoExecution()) + "\"" + CRLF);
			message.append("                        }" + CRLF);
		}		
	    message.append("                     ]" + CRLF);
	    message.append("                  }" + CRLF);
	    message.append("               ]" + CRLF);
	    message.append("            }" + CRLF);
	    return message.toString();
	}

	private String getActions(DataAlert dataAlert) {	
	    if (isVoid(dataAlert.getUrlReportSuite())) {
	        return "";
	    }
	    var message = new StringBuilder();  
	    message.append("            \"actions\": [" + CRLF);
	    message.append("               {" + CRLF);
	    message.append("                  \"type\": \"Action.OpenUrl\"," + CRLF);
	    message.append("                  \"title\": \"Suite Report\"," + CRLF);
	    message.append("                  \"url\": \"" + adjustText(dataAlert.getUrlReportSuite()) + "\"" + CRLF);  // Se eliminó la coma adicional aquí
	    message.append("               }" + CRLF);
	    message.append("            ]" + CRLF);    	
	    return message.toString();
	}
	
	private String getFin() {
	    var message = new StringBuilder();
	    message.append("         }" + CRLF);
	    message.append("      }" + CRLF);
	    message.append("   ]" + CRLF);
	    message.append("}" + CRLF);   	
	    return message.toString();
	}    

    private String regex = "<b[^>]*>";
    private Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);	
	
    private String adjustText(String text) {
        Matcher matcher = pattern.matcher(text);
        String withReplacedBolds = matcher.replaceAll("**");
        withReplacedBolds = withReplacedBolds.replace("</b>", "**");    	
    	
    	return JSONValue.escape(withReplacedBolds);
    }
}
