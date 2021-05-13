package it.polimi.ingsw.server.utils;

import it.polimi.ingsw.server.utils.network.Message;

public interface Observer {
    void update(Message message);
}
