package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author andrew
 */
@javax.persistence.Entity
public class Notification extends Entity{
    
    public final static String NOTIFICATION = "Notificação";

    public Notification() {
        
        seen = false;
    }
    
    
    
    private String title;
    private String description;
    private String type;
    private String picture;
    private String link;
    private User user;
    private boolean seen;
    

    @Column(length = 500)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = 10000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Column(length = 1000)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Notification n = (Notification)e;
        this.dateReg = n.dateReg != null ? n.dateReg : this.dateReg;
        this.description = n.description != null ? n.description : this.description;
        this.picture = n.picture != null ? n.picture : this.picture;
        this.seen = n.seen;
        this.title = n.title != null ? n.title : this.title;
        this.type = n.type != null ? n.type : this.type;
        this.user = n.user != null ? n.user : this.user;
        this.link = n.link != null ? n.link : this.link;
    }

    @Column(length = 5000)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    
    
    
}
