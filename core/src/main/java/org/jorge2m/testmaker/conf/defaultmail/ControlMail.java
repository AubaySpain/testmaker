package org.jorge2m.testmaker.conf.defaultmail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ControlMail {
	
    public static void enviarMail(InfoMail info, String user, String pasword) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", info.getSMTPHost());
        properties.put("mail.smtp.auth", true);
        MiAuthenticator authenticator;
        authenticator=new MiAuthenticator(user,pasword);
        MimeMessage message = new MimeMessage(Session.getDefaultInstance(properties,authenticator));
        message.setFrom(new InternetAddress(info.getFrom()));
        if (info.getTo() != null) {
            InternetAddress toRecipients[] = new InternetAddress[info.getTo().length];
            for (int i = 0; i < info.getTo().length; i++)
                toRecipients[i] = new InternetAddress(info.getTo()[i]);
            
            message.setRecipients(MimeMessage.RecipientType.TO, toRecipients);
        }

        if (info.getCC() != null) {
            InternetAddress CCRecipients[] = new InternetAddress[info.getCC().length];
            for (int i = 0; i < info.getCC().length; i++)
                CCRecipients[i] = new InternetAddress(info.getCC()[i]);
            
            message.setRecipients(MimeMessage.RecipientType.CC, CCRecipients);
        }

        if (info.getCCO() != null) {
            InternetAddress CCORecipients[] = new InternetAddress[info.getCCO().length];
            for (int i = 0; i < info.getCCO().length; i++)
                CCORecipients[i] = new InternetAddress(info.getCCO()[i]);
            
            message.setRecipients(MimeMessage.RecipientType.BCC, CCORecipients);
        }

        if (info.getReplyTo() != null) {
            InternetAddress replyTo[] = new InternetAddress[info.getReplyTo().length];
            for (int i = 0; i < info.getReplyTo().length; i++)
                replyTo[i] = new InternetAddress(info.getReplyTo()[i]);
            
            message.setReplyTo(replyTo);
        }
        
        if (info.getSubject() != null) {
        	message.setSubject(info.getSubject());
        }
        
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart bodyPartText = new MimeBodyPart();
        bodyPartText.setText(info.getText());
        multipart.addBodyPart(bodyPartText);
        if (info.getFiles()!= null) {
            for (int i = 0; i < info.getFiles().length; i++) {
                MimeBodyPart bodyPartFile = new MimeBodyPart();
                bodyPartFile.setDataHandler(new DataHandler(new FileDataSource(info.getFiles()[i])));
                bodyPartFile.setFileName(new File(info.getFiles()[i]).getName());
                multipart.addBodyPart(bodyPartFile);
            }
        }
        
        message.setContent(multipart);
    	Transport.send(message);
    }
}
