package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.ui.Interlocutor;
import it.polimi.ingsw.utils.network.MessageWriter;

public class StringRequest extends Request {
    public StringRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    public MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) throws IllegalInputException {
        super.handleInput (interlocutor, interpreter, writer);
        writer.addProperty (getNamePropertyRequested (), interpreter.listen (getNamePropertyRequested ()));
        return writer;
    }

    public MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter messageWriter, int maxLength) throws IllegalInputException {
        super.handleInput (interlocutor, interpreter, messageWriter);
        String string = interpreter.listen (getNamePropertyRequested ());
        string = string.substring (0, Math.min(string.length (), maxLength));
        messageWriter.addProperty (getNamePropertyRequested (), string);
        return messageWriter;
    }
}
