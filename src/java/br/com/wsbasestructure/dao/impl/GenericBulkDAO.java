package br.com.wsbasestructure.dao.impl;

import br.com.hefesto.domain.impl.Equipment;
import br.com.wsbasestructure.dao.abstracts.AbstractDAO;
import br.com.wsbasestructure.dao.interfaces.ICRUD;
import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class GenericBulkDAO extends AbstractDAO implements ICRUD {

    public GenericBulkDAO(Session session, IHolder holder) {
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
        List list = holder.getEntities();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                Equipment e = (Equipment) list.get(i);
                try {
                    session.save(e);
                } catch (Exception ex) {
                    message.setError("Unknown error: " + ex.getCause().getMessage());
                    fc.setMustContinue(false);
                    result.setStatus(Result.ERROR);
                    result.setMessage(message);
                    result.setHolder(holder);
                }
                if ((i + 1) % 20 == 0) {
                    // flush a batch of inserts and release memory
                    session.flush();
                    session.clear();
                }
            }
            try {
                session.getTransaction().commit();
            } catch (ConstraintViolationException cve) {
                message.setText("conflict");
            }
            if (message.getText() != null && !message.getText().isEmpty()) {
                message.setText(message.getText() + " bulk inserted");
            } else {
                message.setText("bulk inserted");
            }
            result.setStatus(Result.SUCCESS);

        } else {
            message.setError("empty bulk");
            fc.setMustContinue(false);
            result.setStatus(Result.ERROR);
        }
        result.setMessage(message);
        result.setHolder(holder);

        return result;
    }

}
