package com.github.jorge2m.testmaker.conf.defaultmail;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.util.ByteArrayDataSource;

import org.apache.logging.log4j.Level;

import com.github.jorge2m.testmaker.conf.Log4jTM;

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

    public boolean mail(String user, String password, InternetAddress[] to, InternetAddress[] cc, String subject, String texto, List<AttachMail> imgAttach) {
        Properties props = new Properties();
        //props.setProperty("mail.transport.protocol", "smtps"); //
        props.setProperty("mail.host", "smtp.gmail.com"); //
        //props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        //props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587"); //
        props.setProperty("mail.smtp.auth", "true"); //
        props.setProperty("mail.smtp.starttls.enable", "true"); //
        //props.setProperty("mail.smtp.starttls.required", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		       
        Authenticator auth = new SMTPAuthenticator(user, password);
        Session session = Session.getInstance(props, auth);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));
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
            return true;
        }
        catch (MessagingException ex) {
        	Log4jTM.getGlobal().log(Level.FATAL, ex);
        	return false;
        }
    }
}