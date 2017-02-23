package br.com.wsbasestructure.control;

import br.com.wsbasestructure.dao.impl.CRUDCenter;
import br.com.wsbasestructure.dao.interfaces.IPersistenceCenter;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.exceptions.DefaultStructureException;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import br.com.wsbasestructure.view.interfaces.IViewHelper;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class Facade {

    /*
     Attributes
     */
    /**
     *
     */
    private IPersistenceCenter persistenceCenter;
    private final FlowContainer flowC;

    public Facade(FlowContainer flowC) {
        this.flowC = flowC;
    }
    
    

    /**
     *
     * @param view
     * @throws Exception
     *
     * This method will execute all the business rules which is necessary before
     * the main flow occurs.
     */
    private void runBusinessRulesBeforeMainFlow(List rules, IHolder holder) throws Exception {

        for (Object rule : rules) {
            //Just execute it
            ICommand r = (ICommand) rule;
            r.exe(holder, this.flowC);
            //Aftet each loop, it needs to verify if there is any error, then it will decide if the flow have to go on or stop
            verify();
        }
    }

    /**
     *
     * @param view
     * @throws Exception
     *
     * This method will execute all the necessary business rules after the main
     * flow. Examples: Notifications, e-mails (Things that must be executed
     * after the request went and came back from database)
     */
    private void runBusinessRulesAfterMainFlow(List rules, IHolder holder) throws Exception {
        //It needs verify it there is any rule that must be executed after the main flow

        //First, let's execute the verification to check if the flow is blocked.
        verify();

        for (Object rule : rules) {
            //Just execute it
            ICommand r = (ICommand) rule;
            r.exe(holder, this.flowC);
            //Aftet each loop, it needs to verify if there is any error, then it will decide if the flow have to go on or stop
            verify();
        }
    }

    /**
     *
     * @param view
     * @param session
     * @throws Exception
     * @return void This method will execute the main flow of this designed
     * structure. It is commonly used to persist information in the database and
     * execute crucials business logic.
     */
    private void runMainFlow(Session session, IHolder holder, String method, String typeRequest) throws Exception {
        if (typeRequest.equalsIgnoreCase("crud")) {
            persistenceCenter = new CRUDCenter();
        }

        //Now, it needs to execute the action as requested and the persistence center will execute according to the action
        this.flowC.setResult(persistenceCenter.perform(session, holder, this.flowC.getFc(), this.flowC.getResult(), method));
    }

    /**
     *
     * @param session
     * @param view
     * @param holder
     * @param method
     * @param typeRequest
     * @throws Exception
     *
     * This method will serve as container to above methods.
     */
    public void run(Session session, IViewHelper view, IHolder holder, String method, String typeRequest) throws Exception {
        runBusinessRulesBeforeMainFlow(view.getRulesBeforeMainFlow(), holder);
        runMainFlow(session, holder, method, typeRequest);
        runBusinessRulesAfterMainFlow(view.getRulesAfterMainFlow(), holder);
    }

    public String process() {
        IViewHelper v = this.flowC.getViewHelper();
        IHolder h = v.getView(this.flowC);
        try {
            run(this.flowC.getSession(), v, h, this.flowC.getCr().getMethod(), v.getTypeRequest());
        } catch (Exception ex) {
            try {
                DefaultStructureException dse = (DefaultStructureException) ex;
                this.flowC.setResult(dse.getResult());
            } catch (Exception ex2) {
                Message m = new Message();
                m.setError("Não é um erro conhecido (Unknow): " + ex2.getMessage());
                this.flowC.getResult().setMessage(m);
            }
        }

        return v.setView(this.flowC.getResult());
    }

    /**
     *
     * @param view
     *
     * This method verify the existence of flow block. If the flow is blocked,
     * then, it needs to throw a new exception to descontinue the application
     * flow.
     */
    private void verify() throws Exception {
        if (!this.flowC.getFc().isMustContinue()) {
            DefaultStructureException dse = new DefaultStructureException();
            dse.setResult(this.flowC.getResult());
            throw dse;
        }
    }
}
