package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Department extends Entity{
    
    private String name;
    private String description;
    private Set<User> users;

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

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "department"
    )
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Department d = (Department)e;
        this.description = d.description != null ? d.description : this.description;
        this.name = d.name != null ? d.name : this.name;
        this.users = d.users != null ? d.users : this.users;
    }
    
    
    
}
