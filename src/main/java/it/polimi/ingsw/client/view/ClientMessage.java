package it.polimi.ingsw.client.view;

import it.polimi.ingsw.utils.config.JsonHandler;
import it.polimi.ingsw.utils.network.AbstractMessage;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ClientMessage extends AbstractMessage<Update> {

    private Update update;

    public ClientMessage(String messageStr) throws IllegalMessageException {
        super (messageStr);
        this.update = parseForUpdate(getHeader (), messageStr);
    }

    private Update parseForUpdate(Header header, String messageStr) throws IllegalMessageException {
        try {
            return (Update) JsonHandler.getAsJavaObjectFromJSONStr (Update.class, "header", messageStr);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    public ClientMessage (Header header, Update update) {
        super(header);
        this.update = update;
    }

    @Override
    public Update getInfo() {
        return this.update;
    }
}
