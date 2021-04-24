package it.polimi.ingsw.model.gamelogic;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.actions.ActionType;
import java.util.ArrayList;

/**
 * This class represents the Actions performed by a Player in a Game. It's the Java Object that represents the User input
 * from a Client, the interaction from the User with the model in the MVC-pattern-based project
 */
public abstract class Action {

    /**
     * The List of all the ActionTypes after which this Action could be performed
     */
    private final ArrayList<ActionType> requires;

    /**
     * Type of this Action
     */
    private final ActionType type;

    /**
     * Constructor.
     */
    protected Action() {
        this.requires = getRequires();
        this.type = getType();
    }


    /**
     * @return the type of the Action
     */
    protected abstract ActionType getType();


    /**
     * @return the Actions required to perform this Action
     */
    protected abstract ArrayList<ActionType> getRequires();


    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     * @param game -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    protected abstract void perform(Game game, Player player);


    /**
     * @param lastAction
     * @return -> true if this Action can be performed after the Action lastAction, otherwise it returns false. This check
     * is done looking at the type of the lastAction, and if it is contained in the requires of this Action, this Action
     * is valid
     */
    protected boolean isValid(Action lastAction) {
        return this.requires.contains(lastAction.type);
    }

}
