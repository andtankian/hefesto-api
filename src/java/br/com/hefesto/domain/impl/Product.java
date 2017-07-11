package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.Column;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Product extends Entity {

    public final static String PRODUCT = "Produto";
    public final static String COMPONENT = "Componente";

    private String name;
    private String description;
    private String link;
    private String type;

    @Column(length = 1000)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 100000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(length = 100000)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void merge(Entity e) {
        super.merge(e);
        Product p = (Product) e;
        this.description = p.description != null ? p.description : this.description;
        this.link = p.link != null ? p.link : this.link;
        this.name = p.name != null ? p.name : this.name;
        this.type = p.type != null ? p.type : this.type;
    }

}
