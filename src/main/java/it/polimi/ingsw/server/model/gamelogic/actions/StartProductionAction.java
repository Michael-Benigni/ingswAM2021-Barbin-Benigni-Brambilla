package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import java.util.ArrayList;

public class StartProductionAction implements ProductionAction{

    @Override
    public void perform(Game game, Player player){
        ArrayList <Producer> producers = player.getPersonalBoard().getAllProducers();
        for (Producer producer : producers) {
            producer.setAvailableForProduction(true);
        }
    }
}
