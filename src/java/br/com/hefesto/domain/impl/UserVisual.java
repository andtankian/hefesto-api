package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.Column;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class UserVisual extends Entity{
    
    private String profile;
    private String landscape;

    @Column(length = 3000)
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Column(length = 3000)
    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }
}
