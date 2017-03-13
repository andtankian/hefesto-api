package br.com.wsbasestructure.dao.interfaces;

import br.com.wsbasestructure.dto.Result;

/**
 *
 * @author Andrew Ribeiro
 */
public interface ICRUD {

    public Result create();

    public Result read();

    public Result update();

    public Result delete();

}
