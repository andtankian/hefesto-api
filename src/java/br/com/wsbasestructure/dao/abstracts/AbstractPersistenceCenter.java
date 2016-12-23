package br.com.wsbasestructure.dao.abstracts;

import br.com.wsbasestructure.dao.interfaces.IDAO;
import br.com.wsbasestructure.dao.interfaces.IPersistenceCenter;
import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class AbstractPersistenceCenter implements IPersistenceCenter{
    
    private IDAO dao;

    @Override
    public Result perform(Session session, IHolder holder, FlowControl fc, Result result, String method) {
       dao = this.create(session, holder);
       return dao.perform(fc, result, method);
    }   
    
}
