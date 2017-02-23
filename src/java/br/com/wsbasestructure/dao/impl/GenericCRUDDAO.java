package br.com.wsbasestructure.dao.impl;

import br.com.wsbasestructure.dao.abstracts.AbstractDAO;
import br.com.wsbasestructure.dao.interfaces.ICRUD;
import br.com.wsbasestructure.domain.abstracts.Entity;
import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class GenericCRUDDAO extends AbstractDAO implements ICRUD {

    public GenericCRUDDAO(Session session, IHolder holder) {
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
        try {
            session.persist(holder.getEntities().get(0));
            session.getTransaction().commit();
            message.setText("inserted");
            result.setStatus(Result.SUCCESS);
        } catch (Exception e) {
            message.setError(e.getCause().getMessage());
            result.setStatus(Result.ERROR);
            fc.setMustContinue(false);
        } finally {
            result.setHolder(holder);
            result.setMessage(message);
        }

        return result;
    }

    @Override
    public Result update() {
        try {
            session.update(holder.getEntities().get(0));
            session.getTransaction().commit();
            message.setText("updated");
            result.setStatus(Result.SUCCESS);
        } catch (Exception e) {
            message.setError(e.getCause().getMessage());
            result.setStatus(Result.ERROR);
            fc.setMustContinue(false);
        } finally {
            result.setHolder(holder);
            result.setMessage(message);
        }

        return result;
    }

    @Override
    public Result delete() {
        return update();
    }
    
    public void readOne(){
        Entity e = (Entity)holder.getEntities().get(0);
        e = (Entity) session.get(e.getClass(), e.getId());
        holder.getEntities().set(0, e);
        if(e == null){
            message.setError("entity doesn't exist");
            result.setStatus(Result.ERROR);
            result.setMessage(message);
            result.setHolder(holder);
            fc.setMustContinue(false);
        } else {
            message.setText("read");
            result.setStatus(Result.SUCCESS);
            result.setMessage(message);
            result.setHolder(holder);
        }
    }

}
