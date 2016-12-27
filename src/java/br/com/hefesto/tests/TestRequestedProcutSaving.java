package br.com.hefesto.tests;

import br.com.hefesto.domain.impl.Product;
import br.com.hefesto.domain.impl.RequestedProduct;
import br.com.wsbasestructure.dao.abstracts.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andrew Ribeiro
 */
public class TestRequestedProcutSaving {
    
    public static void main(String[] args) {
        
        Connection c = new Connection();
        Session s = c.openSession();
        
        Transaction t = s.beginTransaction();
        
        Product p = new Product();
        p.setName("Caneta Bic");
        p.setLink("http://mercadolivre.com/caneta");
        p.setType(Product.PRODUCT);
        p.setDescription("Caneta azul, bonita");
        p.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        
        RequestedProduct rp = new RequestedProduct();
        rp.setAmount(new Long(5));
        rp.setPrice(new Double(78.58));
        rp.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        rp.setProduct(p);
        rp.setStatusPurchase("Aguardando");
        rp.setType(RequestedProduct.REQUESTED_PRODUCT);
        
        s.persist(rp);
        
        t.commit();
        
        c.closeEverything();
    }
    
}
