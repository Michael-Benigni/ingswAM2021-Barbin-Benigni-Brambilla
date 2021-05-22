package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.MessageWriter;

public class StringRequest extends Request{
    public StringRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    protected MessageWriter handleInput(String string) {
        MessageWriter input = new MessageWriter ();
        input.addProperty (getNamePropertyRequested (), string);
        return input;
    }
}
