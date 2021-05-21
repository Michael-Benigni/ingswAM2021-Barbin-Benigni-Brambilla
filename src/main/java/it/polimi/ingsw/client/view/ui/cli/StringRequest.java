package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.UserInput;

public class StringRequest extends Request{
    protected StringRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    protected UserInput handleInput(String string) {
        UserInput input = new UserInput ();
        input.addProperty (getNamePropertyRequested (), string);
        return input;
    }
}
