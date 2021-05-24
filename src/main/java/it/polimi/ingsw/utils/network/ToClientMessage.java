package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.server.view.ViewUpdate;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ToClientMessage extends AbstractMessage<ViewUpdate>{

    public ToClientMessage(String msg) throws IllegalMessageException {
        //TODO: factory update
        super (msg, ViewUpdate.class, Header.ToClient.class);
    }

    public ToClientMessage(Header.ToClient header, ViewUpdate viewUpdate) {
        super(header, viewUpdate);
    }
}
