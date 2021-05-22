package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.MessageWriter;
import it.polimi.ingsw.utils.config.StringParser;
import java.util.ArrayList;

public class ResourceRequest extends Request{
    private final String separator = " ";

    protected ResourceRequest(String requestDesc, String namePropertyRequested) {
        super (requestDesc, namePropertyRequested);
    }

    @Override
    protected MessageWriter handleInput(String string) {
        StringParser parser = new StringParser (separator);
        ArrayList<String> info = parser.decompose(string);
        MessageWriter resource = new MessageWriter ();
        resource.addProperty ("type", info.get (0));
        resource.addProperty ("amount", info.get (1));
        return resource;
    }
}
