package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
