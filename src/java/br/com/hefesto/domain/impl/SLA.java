package br.com.hefesto.domain.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Andrew Ribeiro
 */
@Embeddable
public class SLA {
    
    private Integer days;
    private Integer hours;
    private Integer minutes;

    @Column(name = "sla_days")
    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    @Column(name = "sla_hours")
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    @Column(name = "sla_minutes")
    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
    
}
