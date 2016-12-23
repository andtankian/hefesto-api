package br.com.wsbasestructure.rules.interfaces;

import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;



/**
 *
 * @author Andrew Ribeiro
 */
public interface ICommand {
    /**
     * 
     * @param holder
     * @param fc
     * @param result
     * @return
     * 
     * This method will execute all the business rules.
     */
    public IHolder exe(IHolder holder, FlowControl fc, Result result);
}
