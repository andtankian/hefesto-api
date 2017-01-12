package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Permission extends Entity{
    
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Permission p1 = (Permission)e;
        this.description = p1.getDescription() != null ? p1.getDescription() : this.description;
        this.name = p1.getName() != null ? p1.getName() : this.name;
        this.users = p1.getUsers() != null ? p1.getUsers() : this.users;
    }
    
    
    
}
