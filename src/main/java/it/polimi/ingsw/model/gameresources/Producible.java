package it.polimi.ingsw.model.gameresources;

import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gamelogic.actions.Player;

public interface Producible extends Resource {
    void onProduced (Player player, Game game) throws Exception;
}
