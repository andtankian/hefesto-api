package br.com.wsbasestructure.dto.impl;

import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.abstracts.AbstractHolder;
import java.util.ArrayList;

/**
 *
 * @author Andrew Ribeiro
 */
public class GenericHolder extends AbstractHolder {

    public GenericHolder() {
        this.entities = new ArrayList<>();
        this.sm = new SearchModel();
    }

}
