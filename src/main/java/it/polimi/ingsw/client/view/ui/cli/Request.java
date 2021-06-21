package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.ui.Interlocutor;
import it.polimi.ingsw.client.view.ui.Interpreter;
import it.polimi.ingsw.utils.network.MessageWriter;

public abstract class Request {
    private final String requestDesc;

    private final String namePropertyRequested;
    protected Request(String requestDesc, String namePropertyRequested) {
        this.requestDesc = requestDesc;
        this.namePropertyRequested = namePropertyRequested;
    }

    protected MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) throws IllegalInputException {
        interlocutor.write (this.getRequestDesc ());
        return writer;
    }

    protected String getNamePropertyRequested() {
        return this.namePropertyRequested;
    }

    public String getRequestDesc() {
        return requestDesc;
    }
}
