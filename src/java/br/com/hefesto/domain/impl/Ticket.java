package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Ticket extends Entity {
    
    public final static String OPENED = "Aberto";
    public final static String PENDING = "Pendente";
    public final static String DEAD_LINE = "Dead Line";
    public final static String CLOSED = "Fechado";
    
    public final static String P5 = "P5";
    public final static String P4 = "P4";
    public final static String P3 = "P3";
    public final static String P2 = "P2";
    public final static String P1 = "P1";
    
    private List<Interaction> interactions;
    private List<RequestedProduct> requestedProducts;

    private Service service;
    private Equipment equipment;
    private String problem;
    private String priority;
    private String resolution;
    private User responsible;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ticket")
    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ticket")
    public List<RequestedProduct> getRequestedProducts() {
        return requestedProducts;
    }

    public void setRequestedProducts(List<RequestedProduct> requestedProducts) {
        this.requestedProducts = requestedProducts;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Service.class)
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Equipment.class)
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Column
    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    @Column
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Ticket t = (Ticket)e;
        this.equipment = t.equipment != null ? t.equipment : this.equipment;
        this.interactions = t.interactions != null ? t.interactions : this.interactions;
        this.priority = t.priority != null ? t.priority : this.priority;
        this.problem = t.problem != null ? t.problem : this.problem;
        this.requestedProducts = t.requestedProducts != null ? t.requestedProducts : this.requestedProducts;
        this.resolution = t.resolution != null ? t.resolution : this.resolution;
        this.responsible = t.responsible != null ? t.responsible : this.responsible;
        this.service = t.service != null ? t.service : this.service;
    }
    
    

}
