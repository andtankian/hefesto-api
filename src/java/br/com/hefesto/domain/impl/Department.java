package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Transient;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Department extends Entity{
    
    private String name;
    private String description;
    
    private List<User> users;
    

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
    
    @Transient
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Department d = (Department)e;
        this.description = d.description != null ? d.description : this.description;
        this.name = d.name != null ? d.name : this.name;
    }   
}
