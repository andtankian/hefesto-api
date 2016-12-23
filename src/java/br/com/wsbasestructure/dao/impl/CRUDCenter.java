package br.com.wsbasestructure.dao.impl;

import br.com.wsbasestructure.dao.abstracts.AbstractPersistenceCenter;
import br.com.wsbasestructure.dao.interfaces.IDAO;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class CRUDCenter extends AbstractPersistenceCenter{

    @Override
    public IDAO create(Session session, IHolder holder) {
       return null;
    }

    
    
}
