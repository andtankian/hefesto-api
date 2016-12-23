package br.com.wsbasestructure.dto;

import java.sql.Timestamp;

/**
 *
 * @author Andrew Ribeiro
 */
public class SearchModel {
    
    private Long amount;
    private Long from;
    private Timestamp[] between;
    private String order;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Timestamp[] getBetween() {
        return between;
    }

    public void setBetween(Timestamp[] between) {
        this.between = between;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    
}
