package br.com.wsbasestructure.exceptions;

import br.com.wsbasestructure.dto.Result;

/**
 *
 * @author Andrew Ribeiro
 */
public class DefaultStructureException extends Exception {

    public DefaultStructureException(Result result) {
        this.result = result;
    }

    public DefaultStructureException() {
    }

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
