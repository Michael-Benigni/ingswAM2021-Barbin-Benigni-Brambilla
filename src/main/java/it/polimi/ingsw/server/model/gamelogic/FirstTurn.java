package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.exception.WrongInitialConfiguration;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;

public class FirstTurn extends Turn {

    //TODO: is necessary to override the add method or the add(nextAction) use the isValid(FirstTurn) ?

    @Override
    public void terminate(Game game) throws WrongInitialConfiguration {
        int initialResources = game.getParams(game.getCurrentPlayer()).getInitialResources();
        if(!game.getCurrentPlayer().getPersonalBoard().checkFirstTurnConditions(initialResources))
            throw new WrongInitialConfiguration();
    }

    void start(Game game) {
        super.start();
        InitialParams params = game.getParams(game.getCurrentPlayer());
        try {
            new FaithPoint(params.getInitialFaithPoints()).activate(game.getCurrentPlayer(), game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
