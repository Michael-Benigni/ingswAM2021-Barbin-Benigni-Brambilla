package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.exception.NoValidActionException;
import it.polimi.ingsw.model.gamelogic.actions.ActionType;
import it.polimi.ingsw.model.gamelogic.actions.Player;

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
     * current state of This Turn
     */
    private TurnState state;

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
         * @param stateName
         */
        TurnState(String stateName) {
            this.stateName = stateName;
        }
    }


    /**
     * Constructor.
     */
    Turn () {
        this.performedActions = new ArrayList<>();
        this.state = TurnState.START;
    }


    /**
     * This method adds the next action to perform if it is a valid action in this Turn
     * @param nextAction action to add
     * @throws NoValidActionException if the action is not valid in this Turn
     */
    void add(Action nextAction) throws NoValidActionException {
        Action lastAction = this.performedActions.get(this.performedActions.size());
        if (lastAction.isValid(this.performedActions) && this.state == TurnState.PLAY) {
            this.performedActions.add(nextAction);
            this.state = TurnState.PLAY;
        }
        else throw new NoValidActionException();
    }


    /**
     * This method sets the state of the Turn in PLAY. After this the Player could perform actions
     */
    public void start() {
        if (this.state == TurnState.START) {
            this.state = TurnState.PLAY;
            this.performedActions.add(new StartTurnAction());
        }
    }


    /**
     * This method ends the Turn of the game, and sets the next Player in the Turn's game
     * @param game -> game in which has been performed this Turn
     */
    public void terminate(Game game) {
        this.state = TurnState.END;
        game.setNextPlayer();
    }
}

class StartTurnAction extends Action {

    @Override
    protected ActionType getType() {
        return ActionType.START;
    }

    @Override
    public void perform(Game game, Player player) {
    }
}
