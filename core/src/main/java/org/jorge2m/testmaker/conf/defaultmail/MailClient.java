package org.jorge2m.testmaker.conf.defaultmail;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.util.ByteArrayDataSource;

import org.apache.logging.log4j.Level;
import org.jorge2m.testmaker.conf.Log4jConfig;

import java.util.*;


public class MailClient {
        
    private class SMTPAuthenticator extends Authenticator {
        
        private PasswordAuthentication authentication;

        public SMTPAuthenticator(String login, String password) {
            this.authentication = new PasswordAuthentication(login, password);
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return this.authentication;
        }
    }

    public void mail(String from, InternetAddress[] to, InternetAddress[] cc, String subject, String texto, ArrayList<AttachMail> imgAttach) {
        String login = "robottestmango@gmail.com";
        String password = "sirrobot";
	        	
        Properties props = new Properties();
        props.setProperty("mail.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
		       
        Authenticator auth = new SMTPAuthenticator(login, password);
        Session session = Session.getInstance(props, auth);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,to);
            if (cc!=null) {
            	message.setRecipients(Message.RecipientType.CC,cc);
            }
            message.setSubject(subject);    

            Multipart multipart = new MimeMultipart();
	                
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(texto, "text/html; charset=UTF-8");
            multipart.addBodyPart(htmlPart);
            if (imgAttach!=null) {
                Iterator<AttachMail> it = imgAttach.listIterator();
                while (it.hasNext()) {
                    AttachMail attachMail = it.next();
                    BodyPart attachBodyPart = new MimeBodyPart();
                    ByteArrayDataSource source = new ByteArrayDataSource(attachMail.getContenido(), attachMail.getType()); 
                    attachBodyPart.setDataHandler(new DataHandler(source));
                    attachBodyPart.setFileName(attachMail.getNameInMail());
                    multipart.addBodyPart(attachBodyPart);
                }
            }
            message.setContent(multipart);
	                
            Transport.send(message);
        }
        catch (MessagingException ex) {
        	Log4jConfig.pLogger.log(Level.FATAL, ex);
        }
    }
}