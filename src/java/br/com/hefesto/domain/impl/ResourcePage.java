package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class ResourcePage extends Entity {

    private Set read;
    private Set write;
    private String name;

   @OneToMany(cascade = CascadeType.ALL,
           fetch = FetchType.EAGER,
           targetEntity = Permission.class)
   @JoinTable(name = "resourcepage_read_permissions")
    public Set getRead() {
        return read;
    }

    public void setRead(Set read) {
        this.read = read;
    }

    @OneToMany(cascade = CascadeType.ALL,
           fetch = FetchType.EAGER,
           targetEntity = Permission.class)
    @JoinTable(name = "resourcepage_write_permissions")
    public Set getWrite() {
        return write;
    }

    public void setWrite(Set write) {
        this.write = write;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        ResourcePage r = (ResourcePage) e;
        this.name = r.name != null ? r.name : this.name;
        this.read = r.read != null ? r.read : this.read;
        this.write = r.write != null ? r.write : this.write;
    }

}
