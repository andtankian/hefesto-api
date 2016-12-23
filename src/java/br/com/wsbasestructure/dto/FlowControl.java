package br.com.wsbasestructure.dto;

/**
 *
 * @author Andrew Ribeiro
 */
public class FlowControl {

    public FlowControl() {
        mustContinue = true;
    }

    private boolean mustContinue;

    public boolean isMustContinue() {
        return mustContinue;
    }

    public void setMustContinue(boolean mustContinue) {
        this.mustContinue = mustContinue;
    }
}
