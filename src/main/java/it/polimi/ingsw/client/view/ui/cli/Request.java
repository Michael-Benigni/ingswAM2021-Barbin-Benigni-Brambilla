package it.polimi.ingsw.client.view.ui.cli;

public abstract class Request {
    private final String requestDesc;

    private final String namePropertyRequested;
    protected Request(String requestDesc, String namePropertyRequested) {
        this.requestDesc = requestDesc;
        this.namePropertyRequested = namePropertyRequested;
    }

    protected abstract Object handleInput(String string/*, ClientState currentState*/);

    protected String getNamePropertyRequested() {
        return this.namePropertyRequested;
    }

    public String getRequestDesc() {
        return requestDesc;
    }
}
