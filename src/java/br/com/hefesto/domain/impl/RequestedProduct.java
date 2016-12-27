package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class RequestedProduct extends Entity{
    
    public final static String REQUESTED_PRODUCT = "Requisição de produto";
    public final static String REQUESTED_COMPONENT = "Requisição de componente";
    
   private Product product;
   private Long amount;
   private Double price;
   private String statusPurchase;
   private String type;
   private Ticket ticket;

   @OneToOne(cascade = CascadeType.ALL,
           fetch = FetchType.EAGER,
           targetEntity = Product.class)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Column
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column
    public String getStatusPurchase() {
        return statusPurchase;
    }

    public void setStatusPurchase(String statusPurchase) {
        this.statusPurchase = statusPurchase;
    }

    @Column
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
   
}
