package it.polimi.ingsw.utils;

import it.polimi.ingsw.utils.network.Sendable;

public interface Observer {
    void onChanged(Sendable sendable);
}
