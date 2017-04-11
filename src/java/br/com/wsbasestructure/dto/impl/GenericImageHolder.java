package br.com.wsbasestructure.dto.impl;

import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.abstracts.AbstractHolder;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author Andrew Ribeiro
 */
public class GenericImageHolder extends AbstractHolder{

    public GenericImageHolder() {
        
        this.entities = new ArrayList<>();
        this.sm = new SearchModel();
        
    }
    
    private InputStream is;

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }
    
}
