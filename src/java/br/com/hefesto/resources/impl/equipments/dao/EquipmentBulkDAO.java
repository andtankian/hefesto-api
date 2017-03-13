package br.com.hefesto.resources.impl.equipments.dao;

import br.com.wsbasestructure.dao.impl.GenericBulkDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class EquipmentBulkDAO extends GenericBulkDAO{

    public EquipmentBulkDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
