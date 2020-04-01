package org.aubay.testmaker.conf.defaultmail;

import javax.mail.PasswordAuthentication;


public class MiAuthenticator extends javax.mail.Authenticator{	
    private PasswordAuthentication authentication;

    public MiAuthenticator(String user, String pasword) {
        String username = user;//user@domain.net
        String password = pasword;
        this.authentication = new PasswordAuthentication(username, password);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return this.authentication;
    }
}
