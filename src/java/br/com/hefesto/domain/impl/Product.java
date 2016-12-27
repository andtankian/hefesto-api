package br.com.hefesto.domain.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import javax.persistence.Column;

/**
 *
 * @author Andrew Ribeiro
 */
@javax.persistence.Entity
public class Product extends Entity{
    
    public final static String PRODUCT = "Produto";
    public final static String COMPONENT = "Componente";
    
    private String name;
    private String description;
    private String link;
    private String type;

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

    @Column
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
}
