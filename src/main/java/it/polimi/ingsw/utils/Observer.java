package it.polimi.ingsw.utils;


import it.polimi.ingsw.server.view.Update;

public interface Observer {
    void update(Update update);
}
