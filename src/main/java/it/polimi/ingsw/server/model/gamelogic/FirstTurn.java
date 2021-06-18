package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.IllegalTurnState;
import it.polimi.ingsw.server.model.exception.NoValidActionException;
import it.polimi.ingsw.server.model.exception.WrongInitialConfiguration;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

public class FirstTurn extends Turn {

    /**
     * @return the Sendable object that will be sent throw the network to notify the Client that is its turn
     */
    @Override
    public Sendable getNextPlayerMessage(Game game) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.YOUR_TURN);
        InitialParams params = game.getParams (game.getCurrentPlayer ());
        writer.addProperty ("additionalMsg", "You have to choose " + params.getInitialResources ()
                + " resources, and " + params.getInitialFaithPoints () + " faith points, before the end of the turn, " +
                "not more, not less. You have also to discard "
                + game.getCurrentPlayer ().getPersonalBoard ().getSlotLeaderCards ().getMaxNumOfCardsDuringGame ()
                + " Leader cards.");
        return writer.write ();
    }

    /**
     * This method adds the next action to perform if it is a valid action in this Turn
     *
     * @param nextAction action to add
     * @throws NoValidActionException if the action is not valid in this Turn
     */
    @Override
    void add(Action nextAction) throws NoValidActionException, IllegalTurnState {
        if (nextAction.isValid(this)&& getState() == TurnState.PLAY) {
            getPerformedActions().add(nextAction);
        } else {
            if (!nextAction.isValid (this))
                throw new NoValidActionException (nextAction);
            throw new IllegalTurnState ();
        }
    }


    @Override
    public void terminate(Game game) throws WrongInitialConfiguration {
        int initialResources = game.getParams(game.getCurrentPlayer()).getInitialResources();
        if(!game.getCurrentPlayer().getPersonalBoard().checkFirstTurnConditions(initialResources))
            throw new WrongInitialConfiguration();
    }



    @Override
    void start(Game game) {
        super.start(game);
        InitialParams params = game.getParams(game.getCurrentPlayer());
        try {
            new FaithPoint(params.getInitialFaithPoints()).activate(game.getCurrentPlayer(), game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
