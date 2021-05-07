package it.polimi.ingsw.server.model.gameresources;

import it.polimi.ingsw.server.model.gamelogic.Player;

public interface Storable extends Resource {
    void store(Player player);
}
