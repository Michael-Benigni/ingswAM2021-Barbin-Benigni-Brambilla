package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.server.view.Update;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ToClientMessage extends AbstractMessage<Update>{

    public ToClientMessage(String msg) throws IllegalMessageException {
        //TODO: factory update
        super (msg, Update.class, Header.ToClient.class);
    }
}
