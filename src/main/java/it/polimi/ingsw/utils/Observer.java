package it.polimi.ingsw.utils;


import it.polimi.ingsw.client.view.ClientMessage;
import it.polimi.ingsw.client.view.Update;

public interface Observer {
    void update(ClientMessage message);

}
