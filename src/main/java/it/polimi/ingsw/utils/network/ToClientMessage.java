package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.client.view.updates.UpdateFactory;
import it.polimi.ingsw.client.view.updates.ViewUpdate;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ToClientMessage extends AbstractMessage<ViewUpdate>{

    public ToClientMessage(String msg) throws IllegalMessageException {
        //TODO: factory update
        super (msg, UpdateFactory.getUpdateType ((Header.ToClient) parseForHeader (msg, Header.ToClient.class)), Header.ToClient.class);
    }

    public ToClientMessage(Header.ToClient header, ViewUpdate viewUpdate) {
        super(header, viewUpdate);
    }
}
