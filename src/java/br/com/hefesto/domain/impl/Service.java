package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.Column;
import javax.persistence.Embedded;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Service extends Entity{
    
    public final static String SERVICE = "Serviço";
    public final static String REPAIR = "Reparo";
    
    private String type;
    private String name;
    private String description;
    private SLA sla;

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Embedded
    public SLA getSla() {
        return sla;
    }

    public void setSla(SLA sla) {
        this.sla = sla;
    }

    @Column
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Service s = (Service)e;
        this.description = s.description != null ? s.description : this.description;
        this.name = s.name != null ? s.name : this.name;
        if(s.sla.getDays() != null){
            this.sla.setDays(s.sla.getDays());
        }
        if(s.sla.getMinutes() != null){
            this.sla.setMinutes(s.sla.getMinutes());
        }
        if(s.sla.getHours() != null){
            this.sla.setHours(s.sla.getHours());
        }
        this.type = s.type != null ? s.type : this.type;
    }
    
    
    
}
