package br.com.wsbasestructure.dto;

import java.sql.Timestamp;
import javax.ws.rs.core.UriInfo;

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
    
    public void getBasics(UriInfo uriInfo){
        String amount = uriInfo.getQueryParameters().get("amount") != null ? uriInfo.getQueryParameters().get("amount").get(0) : "5";
        String from = uriInfo.getQueryParameters().get("from") != null ? uriInfo.getQueryParameters().get("from").get(0) : "0";
        try {
            this.amount = Long.parseLong(amount);
        } catch(NumberFormatException n){
            this.amount = (long)5;
        }
        
        try {
            this.from = Long.parseLong(from) - 1;
        }catch(NumberFormatException n){
            this.from = (long)0;
        }
    }
    
}
