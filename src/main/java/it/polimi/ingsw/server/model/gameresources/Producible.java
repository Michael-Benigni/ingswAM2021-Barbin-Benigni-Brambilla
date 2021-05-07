package it.polimi.ingsw.server.model.gameresources;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;

public interface Producible extends Resource {
    void onProduced (Player player, Game game) throws Exception;
}
