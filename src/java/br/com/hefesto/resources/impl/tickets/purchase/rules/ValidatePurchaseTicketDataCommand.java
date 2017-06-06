package br.com.hefesto.resources.impl.tickets.purchase.rules;

import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Product;
import br.com.hefesto.domain.impl.RequestedProduct;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidatePurchaseTicketDataCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Message m = new Message();
        boolean isValid = true;
        Session s = flowContainer.getSession();
        Transaction tr = s.getTransaction().isActive() ? s.getTransaction() : s.beginTransaction();

        Ticket t = (Ticket) holder.getEntities().get(0);

        Set rps = t.getRequestedProducts();

        if (rps.isEmpty()) {
            isValid = false;
            m.setError("request products is invalid");
        } else {
            for (Object rp : rps) {
                RequestedProduct r = (RequestedProduct) rp;
                if (r.getAmount() <= 0) {
                    isValid = false;
                    m.setError("invalid amount");
                    break;
                } else {
                    Product ploaded = (Product) s.get(Product.class, r.getProduct().getId());
                    if (ploaded == null) {
                        isValid = false;
                        m.setError(new StringBuilder("product ").append(r.getProduct().getId()).append(" doesn't exist").toString());
                    } else {
                        r.setProduct(ploaded);
                    }
                }
            }
            
            if (isValid) {
                Long idResponsible = t.getResponsible().getId();
                if(idResponsible == null || idResponsible < 0){
                    m.setError("invalid responsible id");
                    isValid = false;
                } else {
                    User responsibleLoaded = (User) s.get(User.class, idResponsible);
                    if(responsibleLoaded == null){
                        m.setError("responsible doesn't exist");
                        isValid = false;
                    }
                    t.setResponsible(responsibleLoaded);
                    for (Object interaction : t.getInteractions()) {
                        ((Interaction)interaction).setUser(responsibleLoaded);
                    }
                }
            }
        }

        if (!isValid) {
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setHolder(holder);
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setMessage(m);
        }
        return holder;
    }

}
