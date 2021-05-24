package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.utils.network.MessageWriter;

public class StringRequest extends Request{
    public StringRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    public MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) {
        super.handleInput (interlocutor, interpreter, writer);
        writer.addProperty (getNamePropertyRequested (), interpreter.listen ());
        return writer;
    }
}
