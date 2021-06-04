package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.IllegalTurnState;
import it.polimi.ingsw.server.model.exception.NoValidActionException;
import it.polimi.ingsw.server.model.exception.WrongInitialConfiguration;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.server.model.gamelogic.actions.PayAction;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayList;

/**
 * This class represents the Turn in a Game.
 */
public class Turn {

    /**
     * list of performed Actions in this Turn
     */
    private ArrayList<Action> performedActions;

    /**
     * list of all the payments that can be reverted if something goes wrong
     */
    private ArrayList<PayAction> payActions;

    /**
     * current state of This Turn
     */
    private TurnState state;

    /**
     * token
     */
    private TurnToken token;


    /**
     *
     */
    private enum TurnToken {
        AVAILABLE(),
        UNAVAILABLE(),
        AVAILABLE_FOR_PRODUCTION();
    }
    /**
     * Inner enum that represents the possible states of the Turn
     */
    private enum TurnState {

        /**
         * the initial state of the Turn
         */
        START("start"),

        /**
         * the state in which could be performed actions in the Game of this Turn
         */
        PLAY("play"),

        /**
         * the final state of the Turn
         */
        END("end");


        /**
         * name of the state
         */
        private final String stateName;


        /**
         * Constructor.
         *
         * @param stateName
         */
        TurnState(String stateName) {
            this.stateName = stateName;
        }

    }

    /**
     * Constructor.
     */
    Turn() {
        this.performedActions = new ArrayList<>();
        this.payActions = new ArrayList<>();
        this.state = TurnState.START;
    }


    /**
     * @return the Sendable object that will be sent throw the network to notify the Client that is its turn
     */
    public Sendable getNextPlayerMessage(Game game) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.YOUR_TURN);
        return writer.write ();
    }


    /**
     * This method adds the next action to perform if it is a valid action in this Turn
     *
     * @param nextAction action to add
     * @throws NoValidActionException if the action is not valid in this Turn
     */
    void add(Action nextAction) throws NoValidActionException, IllegalTurnState {
        if (nextAction.isValid(this) && this.state == TurnState.PLAY) {
            this.performedActions.add(nextAction);
        } else {
            if (!nextAction.isValid (this))
                throw new NoValidActionException ();
            throw new IllegalTurnState ();
        }
    }


    /**
     * This method sets the state of the Turn in PLAY. After this the Player could perform actions
     */
    void start(Game game) {
        if (this.state == TurnState.START) {
            this.state = TurnState.PLAY;
            this.token = TurnToken.AVAILABLE;
            this.performedActions.add(new StartTurnAction());
        }
    }


    /**
     * This method ends the Turn of the game, and sets the next Player in the Turn's game
     *
     * @param game -> game in which has been performed this Turn
     */
    public void terminate(Game game) throws WrongInitialConfiguration {
        this.state = TurnState.END;
    }

    public void addUndoableAction(PayAction action) {
        this.payActions.add(action);
    }

    private ArrayList<PayAction> getUndoableActions() {
        return this.payActions;
    }

    public void clearCache() {
        this.payActions.clear();
    }

    public void undo(Game game, Player player) throws Exception {
        for (PayAction payAction : this.getUndoableActions()) {
            payAction.getUndoAction().perform(game, player);
        }
    }

    public void consumeToken() throws NoValidActionException {
        if(this.token == TurnToken.AVAILABLE) {
            this.token = TurnToken.UNAVAILABLE;
        }
        else
            throw new NoValidActionException();
    }

    public void startProductionPhase() throws NoValidActionException {
        if(this.token == TurnToken.AVAILABLE) {
            this.token = TurnToken.AVAILABLE_FOR_PRODUCTION;
        }
        else
            throw new NoValidActionException();
    }

    public void consumeTokenInProductionPhase() throws NoValidActionException {
        if(this.token != TurnToken.AVAILABLE_FOR_PRODUCTION)
            throw new NoValidActionException();
    }

    public void endProductionPhase() throws NoValidActionException {
        if(this.token == TurnToken.AVAILABLE_FOR_PRODUCTION) {
            this.token = TurnToken.UNAVAILABLE;
        }
        else
            throw new NoValidActionException();
    }
}

class StartTurnAction implements Action {
    @Override
    public void perform(Game game, Player player) {
    }

    /**
     * @param turn turn in which is checked the validity of this action
     * @return true if this Action can be performed after the Action performedActions, otherwise it returns false. This check
     * is done looking at the type of the performedActions, and if it is contained in the requires of this Action, this Action
     * is valid
     */
    @Override
    public boolean isValid(Turn turn) {
        return false;
    }
}
