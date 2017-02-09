package br.com.wsbasestructure.dto;

import br.com.wsbasestructure.dto.interfaces.IHolder;

/**
 *
 * @author Andrew Ribeiro
 */
public class Result {

    public static final int FATAL_ERROR = -2;
    public static final int ERROR = -1;
    public static final int SUCCESS = 1;

    private IHolder holder;
    private Message message;
    private int status;

    public Result() {

        this.message = new Message();
    }

    public IHolder getHolder() {
        return holder;
    }

    public void setHolder(IHolder holder) {
        this.holder = holder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
