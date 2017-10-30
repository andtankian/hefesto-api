package br.com.wsbasestructure.dto.impl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author andrew
 */
public class HefestoAuthenticator extends Authenticator{

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("hefesto.noreply@etec.sp.gov.br", "VcR#qmT+");
    }
    
    
    
}
