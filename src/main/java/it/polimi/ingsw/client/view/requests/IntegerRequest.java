package it.polimi.ingsw.client.view.requests;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.ui.Interlocutor;
import it.polimi.ingsw.client.view.ui.Interpreter;
import it.polimi.ingsw.utils.network.MessageWriter;

public class IntegerRequest extends Request{

    public IntegerRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    public MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) throws IllegalInputException {
        super.handleInput (interlocutor, interpreter, writer);
        try {
            writer.addProperty (getNamePropertyRequested(), Integer.parseInt (interpreter.listen (getNamePropertyRequested ())));
        } catch (Exception e) {
            System.out.printf ("Error: %s\n", e.getMessage ());
            throw new IllegalInputException();
        }
        return writer;
    }
}
