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
public class User extends Entity{
    
    private String login;
    private String password;
    private String fullName;
    private Department department;
    
    @Column
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            targetEntity = Department.class)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        User u = (User)e;
        this.department = u.department != null ? u.department : this.department;
        this.fullName = u.fullName != null ? u.fullName : this.fullName;
        this.login = u.login != null ? u.login : this.login;
        this.password = u.password != null ? u.password : this.password;
    }
    
    
}
