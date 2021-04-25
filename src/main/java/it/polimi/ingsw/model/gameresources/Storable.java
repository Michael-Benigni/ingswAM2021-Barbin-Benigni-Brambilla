package it.polimi.ingsw.model.gameresources;

import it.polimi.ingsw.model.gamelogic.actions.Player;

public interface Storable extends Resource {
    void store(Player player);
}
