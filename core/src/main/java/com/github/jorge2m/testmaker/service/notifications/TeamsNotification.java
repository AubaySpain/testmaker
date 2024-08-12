package com.github.jorge2m.testmaker.service.notifications;

import static org.apache.http.impl.client.HttpClients.createDefault;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.github.jorge2m.testmaker.domain.suitetree.SuiteTM;
import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.github.jorge2m.testmaker.service.notifications.exceptions.UnsendNotification;
import com.github.jorge2m.testmaker.conf.Log4jTM;

public abstract class TeamsNotification {

	protected static final String ICON_TESTMAKER = "https://cookies-manejoderedes.com/wp-content/uploads/2020/09/bot-e1664451412540.png";
	
	protected abstract String getBodyMessage(DataAlert dataAlert);
	
	public static TeamsNotification make(SuiteTM suite) {
		String teamsChannel = suite.getInputParams().getTeamsChannel();
		if (!isVoid(teamsChannel)) {
			if (TeamsNotificationWorkflow.isUrlChannelOk(teamsChannel)) {
				return new TeamsNotificationWorkflow();
			}
			if (TeamsNotificationWebhook.isUrlChannelOk(teamsChannel)) {
				return new TeamsNotificationWebhook();
			}
			throw new NotFoundException("Parameter TeamsChannel incorrect, value: " + teamsChannel);
		}
		return new TeamsNotificationWorkflow();
	}
	
	public String getTeamsURL(SuiteTM suite) {
		return suite.getInputParams().getTeamsChannel();
	}
	
	protected void sendToTeams(DataAlert dataAlert, String teamsUrl) {
		Log4jTM.getLogger().info("Sending notification via url " + teamsUrl);
        try (var httpClient = createDefault()) {
            var post = new HttpPost(teamsUrl);
            post.addHeader("Content-Type", "application/json");
            String bodyMessage = getBodyMessage(dataAlert);
            var entity = new StringEntity(bodyMessage, "UTF-8");
            post.setEntity(entity);
            var response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode < 200 || statusCode >= 300) {
            	String errorMessage = String.format(
            			"HttpError %s sending Teams Notification with body message %s", 
            			response.getStatusLine().getStatusCode(), bodyMessage);
                throw new UnsendNotification(errorMessage);
            }
        } catch (Exception e) {
        	Log4jTM.getLogger().error("Problem sending Teams Notification to URL {}. {}. {}", teamsUrl, e.getMessage(), e.getStackTrace());
        }
	}	
	
    protected static boolean isVoid(String value) {
    	return value==null || "".compareTo(value)==0;
    }
	
}
