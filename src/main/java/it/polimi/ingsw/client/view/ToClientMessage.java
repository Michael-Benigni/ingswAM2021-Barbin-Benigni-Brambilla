package it.polimi.ingsw.client.view;

import it.polimi.ingsw.utils.config.JsonHandler;
import it.polimi.ingsw.utils.network.AbstractMessage;
import it.polimi.ingsw.utils.network.ToServer;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ToClientMessage extends AbstractMessage<Update> {

    private Update update;

    public ToClientMessage(String messageStr) throws IllegalMessageException {
        super (messageStr);
        this.update = parseForUpdate((ToClient) getHeader (), messageStr);
    }

    private Update parseForUpdate(ToClient toClient, String messageStr) throws IllegalMessageException {
        try {
            return (Update) JsonHandler.getAsJavaObjectFromJSONStr (Update.class, "fromClient", messageStr);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    public ToClientMessage(ToServer toServer, Update update) {
        super(toServer);
        this.update = update;
    }

    @Override
    public Update getInfo() {
        return this.update;
    }
}
