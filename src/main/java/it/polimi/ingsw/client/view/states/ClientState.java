package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.ui.cli.Request;
import java.util.ArrayList;

public abstract class ClientState {
    private boolean isEnded;
    private ArrayList<Request> requests;

    protected ClientState() {
        this.isEnded = false;
        this.requests = new ArrayList<> ();
    }

    protected void addRequests(Request request) {
        this.requests.add (request);
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
