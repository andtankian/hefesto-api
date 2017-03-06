package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.Column;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Equipment extends Entity{
    
    private String name;
    private String description;
    private String patrimonial;

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

    @Column(unique = true)
    public String getPatrimonial() {
        return patrimonial;
    }

    public void setPatrimonial(String patrimonial) {
        this.patrimonial = patrimonial;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Equipment eq = (Equipment)e;
        this.description = eq.description != null ? eq.description : this.description;
        this.name = eq.name != null ? eq.name : this.name;
        this.patrimonial = eq.patrimonial != null ? eq.patrimonial : this.patrimonial;
    }
    
    
}
