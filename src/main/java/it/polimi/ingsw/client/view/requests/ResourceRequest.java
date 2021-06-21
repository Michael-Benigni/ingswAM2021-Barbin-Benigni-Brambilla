package it.polimi.ingsw.client.view.requests;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.ui.Interlocutor;
import it.polimi.ingsw.client.view.ui.Interpreter;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.config.StringParser;
import java.util.ArrayList;

public class ResourceRequest extends Request{
    private final String separator = " ";

    public ResourceRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    public MessageWriter handleInput(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer) throws IllegalInputException {
        super.handleInput (interlocutor, interpreter, writer);
        interlocutor.write ("The format of the input must be \"RESOURCE_TYPE AMOUNT\"");
        StringParser parser = new StringParser (separator);
        MessageWriter resource = new MessageWriter ();
        ArrayList<String> info = parser.decompose(interpreter.listen (getNamePropertyRequested ()));
        try {
            resource.addProperty ("resourceType", info.get (0));
            resource.addProperty ("amount", Integer.parseInt (info.get (1)));
        } catch (Exception e) {
            throw new IllegalInputException ();
        }
        writer.addProperty (getNamePropertyRequested (), resource.getInfo());
        return writer;
    }
}
