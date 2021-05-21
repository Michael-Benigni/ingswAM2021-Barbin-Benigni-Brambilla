package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.network.ToClientMessage;

public interface Observer {
    void update(ToClientMessage message);
}
