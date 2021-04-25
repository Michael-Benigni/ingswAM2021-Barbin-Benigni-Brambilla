package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.model.gamelogic.actions.Player;
import it.polimi.ingsw.model.gamelogic.actions.ActionType;
import java.util.ArrayList;

/**
 * This class represents the Actions performed by a Player in a Game. It's the Java Object that represents the User input
 * from a Client, the interaction from the User with the model in the MVC-pattern-based project
 */
public abstract class Action {

    /**
     * Type of this Action
     */
    private final ActionType type;

    /**
     * Constructor.
     */
    protected Action() {
        this.type = getType();
    }


    /**
     * @return the type of the Action
     */
    protected abstract ActionType getType();


    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     * @param game -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    public abstract void perform(Game game, Player player) throws Exception;


    /**
     * @param performedActions
     * @return -> true if this Action can be performed after the Action performedActions, otherwise it returns false. This check
     * is done looking at the type of the performedActions, and if it is contained in the requires of this Action, this Action
     * is valid
     */
    protected boolean isValid(ArrayList<Action> performedActions) {
        ArrayList<ActionType> performedActionTypes = new ArrayList<>();
        for (Action action : performedActions)
            performedActionTypes.add(action.type);
        switch (this.type) {
            case ANYTIME:
                return true;
            case MUTUAL_EX:
                return !performedActionTypes.contains(ActionType.MUTUAL_EX);
            case END:
                return !performedActionTypes.contains(ActionType.END);
            case START:
                return !performedActionTypes.contains(ActionType.START);
            default:
                return false;
        }
    }

}
