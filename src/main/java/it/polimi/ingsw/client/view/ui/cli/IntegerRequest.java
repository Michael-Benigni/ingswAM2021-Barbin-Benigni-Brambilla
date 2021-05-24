package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.utils.network.MessageWriter;

public class IntegerRequest extends Request{

    public IntegerRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    public MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) {
        super.handleInput (interlocutor, interpreter, writer);
        writer.addProperty (getNamePropertyRequested(), Integer.parseInt (interpreter.listen ()));
        return writer;
    }
}
