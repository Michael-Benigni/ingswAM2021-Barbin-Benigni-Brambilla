package it.polimi.ingsw.utils;

import it.polimi.ingsw.client.view.ToClientMessage;

public interface Observer {
    void update(ToClientMessage message);
}
