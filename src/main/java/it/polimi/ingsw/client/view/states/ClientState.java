package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.ui.cli.Request;

public abstract class ClientState {
    private boolean isEnded;

    protected ClientState() {
        this.isEnded = false;
    }

    public abstract String getOptions();

    public abstract Request nextRequest(String input);

    public abstract ClientState getNextState();

    public boolean isEnded() {
        return this.isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = false;
    }
}
