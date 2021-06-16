package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NoValidActionException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;

import java.util.ArrayList;

public class StartProductionAction implements ProductionAction {

    @Override
    public void perform(Game game, Player player){
        ArrayList <Producer> producers = player.getPersonalBoard().getAllProducers();
        for (Producer producer : producers) {
            producer.setAvailableForProduction(true);
        }
        player.getPersonalBoard ().getTempContainer ().setAsContainerForProduction(true);
    }

    @Override
    public boolean isValid(Turn turn) {
        try {
            turn.startProductionPhase ();
        } catch (NoValidActionException e) {
            return false;
        }
        return true;
    }
}
