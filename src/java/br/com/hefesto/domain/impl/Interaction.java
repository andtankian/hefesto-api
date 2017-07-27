package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Interaction extends Entity{
    
    public final static String OPENING = "Abertura de ticket";
    public final static String UPDATING_PRIORITY = "Prioridade atualizada";
    public final static String RESOLVING_COMPLETELY = "Fechamento de ticket";
    public final static String UPDATING_TICKET = "Atualização de ticket";
    public final static String REFUSING_TICKET = "Recusação de ticket";
    public final static String ACCEPTING_TICKET = "Aceitação de ticket";
    
    private User user;
    private String type;
    private String update;
    private Ticket ticket;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Ticket.class)
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Interaction i = (Interaction)e;
        this.type = i.type != null ? i.type : this.type;
        this.user = i.user != null ? i.user : this.user;
        this.update = i.update != null ? i.update : this.update;
    }

    @Column(length = 10000)
    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
    
    
}
