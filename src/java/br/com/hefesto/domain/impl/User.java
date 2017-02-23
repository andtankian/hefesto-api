package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"login"}, name = "duplicate_login")
    ,@UniqueConstraint(columnNames = {"email"}, name = "duplicate_email")})
public class User extends Entity {

    private String login;
    private String email;
    private String password;
    private String fullName;
    private String type;
    private Department department;
    private Set groups;

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
            fetch = FetchType.LAZY,
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
        User u = (User) e;
        this.department = u.department != null ? u.department : this.department;
        this.fullName = u.fullName != null ? u.fullName : this.fullName;
        this.login = u.login != null ? u.login : this.login;
        this.password = u.password != null ? u.password : this.password;
        this.type = u.type != null ? u.type : this.type;
        this.email = u.email != null ? u.email : this.email;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users", targetEntity = Groups.class)
    public Set getGroups() {
        return groups;
    }

    public void setGroups(Set groups) {
        this.groups = groups;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
