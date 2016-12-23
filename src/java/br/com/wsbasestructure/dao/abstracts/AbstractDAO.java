package br.com.wsbasestructure.dao.abstracts;

import br.com.wsbasestructure.dao.interfaces.IDAO;
import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class AbstractDAO implements IDAO {

    protected Session session;
    protected Result result;
    protected Message message;
    protected FlowControl fc;
    protected IHolder holder;

    public AbstractDAO(Session session, IHolder holder) {
        this.session = session;
        this.holder = holder;
    }

    @Override
    public Result perform(FlowControl fc, Result result, String method) {
        this.fc = fc;
        this.result = result;
        this.message = new Message();

        return null;
    }
}
