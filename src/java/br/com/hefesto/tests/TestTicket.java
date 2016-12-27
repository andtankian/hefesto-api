package br.com.hefesto.tests;

import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dao.abstracts.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andrew Ribeiro
 */
public class TestTicket {
    
    public static void main(String[] args) {
        Connection c = new Connection();
        Session s = c.openSession();

        Transaction t = s.beginTransaction();
        
        Ticket ticket = new Ticket();
        ticket.setPriority(Ticket.P5);
        ticket.setStatus(Ticket.OPENED);
        ticket.setInteractions(new ArrayList<>());
        
        Interaction interaction = new Interaction();
        interaction.setTicket(ticket);
        interaction.setUser((User)s.load(User.class, (long)1));
        interaction.setType(Interaction.OPENING);
        
        ticket.getInteractions().add(interaction);
        
        Service service = new Service();
        service.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        service.setDescription("Montagem de sala para reuniões");
        service.setName("Montagem de sala para reuniões");
        service.setType(Service.SERVICE);
        
        ticket.setService(service);
        
        ticket.setProblem("Dia 23 terá rematrícula e nós, secretaria, precisamos que a sala da gestão esteja arrumada.");
        
        s.persist(ticket);
        
        t.commit();
        
        t = s.beginTransaction();
        
        //now, let's edit it to insert new a interaction, changing priority and giving a proper update status
        Interaction i2 = new Interaction();
        i2.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        i2.setType(Interaction.UPDATING_PRIORITY);
        i2.setUser((User)s.load(User.class, (long)1));
        i2.setTicket(ticket);
        
        ticket.getInteractions().add(i2);
        
        ticket.setPriority(Ticket.P4);
        
        s.update(ticket);
        
        t.commit();
        
        s.close();
        
        s = c.openSession();
        t = s.beginTransaction();
        
        Interaction i3 = new Interaction();
        i3.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        i3.setTicket(ticket);
        i3.setType(Interaction.RESOLVING_COMPLETELY);
        i3.setUser((User)s.load(User.class, (long)1));
        
        ticket.getInteractions().add(i3);
        
        ticket.setResolution("Foi arrumado a sala da gestão com sucesso utilizando projetor móvel e cabos de rede, além dos computadores da sala dos professores.");
        ticket.setStatus(Ticket.CLOSED);
        ticket.setResponsible(i3.getUser());
        
        s.update(ticket);
        
        t.commit();
        
        
        c.closeEverything();
        
    }
    
}
