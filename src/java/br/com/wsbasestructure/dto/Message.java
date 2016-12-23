package br.com.wsbasestructure.dto;

/**
 *
 * @author Andrew Ribeiro
 */
public class Message {

    private String text;
    private String error;

    public Message() {
        text = "";
        error = "";
    }

    public Message(String error) {
        this.error = error;
    }

    public Message(String text, String error) {
        this.text = text;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
