package br.com.wsbasestructure.domain.abstracts;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Entity implements Serializable{
    
    protected Long id;
    protected Timestamp dateReg;
    protected String status;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDateReg() {
        return dateReg;
    }

    public void setDateReg(Timestamp dateReg) {
        this.dateReg = dateReg != null ? dateReg : new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void merge(Entity e){
        this.dateReg = e.dateReg != null ? e.dateReg : this.dateReg;
        if(e.status != null && e.status.equalsIgnoreCase("activate")) this.status = null;
        else if(e.status != null) this.status = e.status;
    }
    
}