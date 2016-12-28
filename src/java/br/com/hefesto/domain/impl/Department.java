package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.Column;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Department extends Entity{
    
    private String name;
    private String description;
    

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

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Department d = (Department)e;
        this.description = d.description != null ? d.description : this.description;
        this.name = d.name != null ? d.name : this.name;
    }
    
    
    
}
