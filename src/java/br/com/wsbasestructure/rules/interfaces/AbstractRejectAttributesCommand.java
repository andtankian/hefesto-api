package br.com.wsbasestructure.rules.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class AbstractRejectAttributesCommand implements ICommand{

    protected List rejects;
    public AbstractRejectAttributesCommand(String[] rejects) {
        this.rejects = rejects.length > 0 ? Arrays.asList(rejects) : new ArrayList<>();
    }
    
    
    
}
