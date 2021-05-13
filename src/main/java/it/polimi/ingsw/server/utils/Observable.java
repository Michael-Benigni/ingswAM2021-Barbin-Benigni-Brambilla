package it.polimi.ingsw.server.utils;

import it.polimi.ingsw.server.utils.network.Message;


public interface Observable {
    void onChanged(Message message);
}
