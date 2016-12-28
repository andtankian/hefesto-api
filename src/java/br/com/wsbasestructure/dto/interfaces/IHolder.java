package br.com.wsbasestructure.dto.interfaces;

import br.com.wsbasestructure.dto.SearchModel;
import java.util.List;

/**
 *
 * @author Andrew Ribeiro
 */
public interface IHolder {

    public List getEntities();
    public void setEntities(List entities);
    public SearchModel getSm();
    public void setSm(SearchModel sm);
}
