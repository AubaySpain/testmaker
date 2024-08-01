package com.github.jorge2m.testmaker.service.notifications;

import static org.apache.http.impl.client.HttpClients.createDefault;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;
import com.github.jorge2m.testmaker.conf.Log4jTM;

public abstract class TeamsNotification {

	protected abstract String getTeamsURL(SuiteTM suite);
	protected abstract String getBodyMessage(DataAlert dataAlert);
	
	public static TeamsNotification make(SuiteTM suite) {
		if (!isVoid(suite.getInputParams().getTeamsChannel())) {
			new TeamsNotificationWebhook();
		}
		return new TeamsNotificationWorkflow();
	}
	
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
	
    protected static boolean isVoid(String value) {
    	return value==null || "".compareTo(value)==0;
    }
	
}
