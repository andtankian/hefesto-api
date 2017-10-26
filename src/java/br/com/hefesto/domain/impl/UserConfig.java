package br.com.hefesto.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author andrew
 */
@Entity
public class UserConfig extends br.com.wsbasestructure.domain.abstracts.Entity{
    
    private String forgotPasswordCurrentToken;

    @Column(length = 10000)
    public String getForgotPasswordCurrentToken() {
        return forgotPasswordCurrentToken;
    }

    public void setForgotPasswordCurrentToken(String forgotPasswordCurrentToken) {
        this.forgotPasswordCurrentToken = forgotPasswordCurrentToken;
    }
    
}
