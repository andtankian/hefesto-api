package br.com.wsbasestructure.rules.interfaces;

import java.util.List;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class AbstractAcceptAttributesCommand implements ICommand{

    public AbstractAcceptAttributesCommand(String[] accepts, List rejects) {
        this.accepts = accepts;
        this.rejects = rejects;
    }   
    
    protected String[] accepts;
    protected List rejects;
    
}
