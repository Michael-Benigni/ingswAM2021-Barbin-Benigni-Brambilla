package it.polimi.ingsw.client.view;

import it.polimi.ingsw.server.utils.network.Message;

public interface MessageHandler {
    void onLoop(Message msg);
}
