package br.com.wsbasestructure.dto.abstracts;


import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import java.util.List;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class AbstractHolder implements IHolder {
    
    protected List entities;
    protected SearchModel sm;

    @Override
    public List getEntities() {
        return this.entities;
    }

    @Override
    public void setEntities(List entities) {
        this.entities = entities;
    }

    @Override
    public SearchModel getSm() {
        return sm;
    }

    @Override
    public void setSm(SearchModel sm) {
        this.sm = sm;
    }
       

}