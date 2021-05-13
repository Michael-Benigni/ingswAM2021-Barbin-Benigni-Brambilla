package it.polimi.ingsw.server.utils.network;

public interface MessageHandler {
    void onLoop(Message msg);
}
