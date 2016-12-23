package br.com.wsbasestructure.dao.interfaces;

import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;


/**
 *
 * @author Andrew Ribeiro
 */
public interface IPersistenceCenter {
    
    public Result perform(Session session, IHolder holder, FlowControl fc, Result result, String method);
    public IDAO create(Session session, IHolder holder);
    
}
