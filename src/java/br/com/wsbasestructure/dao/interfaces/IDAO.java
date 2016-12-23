package br.com.wsbasestructure.dao.interfaces;

import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Result;


/**
 *
 * @author Andrew Ribeiro
 */
public interface IDAO {
    public Result perform(FlowControl fc, Result result, String method);
}
