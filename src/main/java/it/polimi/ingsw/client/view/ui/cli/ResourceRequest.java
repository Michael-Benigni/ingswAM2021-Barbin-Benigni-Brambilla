package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.config.StringParser;
import java.util.ArrayList;

public class ResourceRequest extends Request{
    private final String separator = " ";

    protected ResourceRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    protected MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) {
        super.handleInput (interlocutor, interpreter, writer);
        StringParser parser = new StringParser (separator);
        ArrayList<String> info = parser.decompose(interpreter.listen ());
        writer.addProperty ("type", info.get (0));
        writer.addProperty ("amount", info.get (1));
        return writer;
    }
}
