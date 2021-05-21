package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.UserInput;

public class IntegerRequest extends Request{

    protected IntegerRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    protected UserInput handleInput(String string) {
        UserInput input = new UserInput ();
        input.addProperty (getNamePropertyRequested(), Integer.parseInt (string));
        return input;
    }
}
