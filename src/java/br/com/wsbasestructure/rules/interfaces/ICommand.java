package br.com.wsbasestructure.rules.interfaces;

import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;



/**
 *
 * @author Andrew Ribeiro
 */
public interface ICommand {
    /**
     * 
     * @param holder
     * @param flowContainer
     * @return
     * 
     * This method will execute all the business rules.
     */
    public IHolder exe(IHolder holder, FlowContainer flowContainer);
}
