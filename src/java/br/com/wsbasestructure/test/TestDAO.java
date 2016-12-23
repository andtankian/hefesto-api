package br.com.wsbasestructure.test;

import br.com.wsbasestructure.dao.abstracts.AbstractDAO;
import br.com.wsbasestructure.dao.interfaces.ICRUD;
import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class TestDAO extends AbstractDAO implements ICRUD {

    public TestDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result perform(FlowControl fc, Result result, String method) {
        super.perform(fc, result, method);

        if (method.equalsIgnoreCase("post")) {
            return create();
        } else if (method.equalsIgnoreCase("get")) {
            return read();
        } else if (method.equalsIgnoreCase("put")) {
            return update();
        } else {
            return delete();
        }
    }

    @Override
    public Result create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result read() {
        message.setText("read");
        result.setMessage(message);
        result.setHolder(holder);
        result.setStatus(Result.SUCCESS);
        
        return result;
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
