package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.MessageWriter;

public class IntegerRequest extends Request{

    public IntegerRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    protected MessageWriter handleInput(String string) {
        MessageWriter input = new MessageWriter ();
        input.addProperty (getNamePropertyRequested(), Integer.parseInt (string));
        return input;
    }
}
