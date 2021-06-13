package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.NoValidActionException;
import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.model.gamelogic.FirstTurn;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.Turn;

/**
 * This class represents the Actions performed by a Player in a Game. It's the Java Object that represents the User input
 * from a Client, the interaction from the User with the model in the MVC-pattern-based project
 */
public interface Action extends Command {

    /**
     * This is the method that performs this Action in the Game, and changes the actual state of the Game
     * @param game -> the Game on which this Action will be performed
     * @param player -> the Player who perform this Action
     */
    void perform(Game game, Player player) throws Exception;


    /**
     * @param turn turn in which is checked the validity of this action
     * @return true if this Action can be performed after the Action performedActions, otherwise it returns false. This check
     * is done looking at the type of the performedActions, and if it is contained in the requires of this Action, this Action
     * is valid
     */
    default boolean isValid(Turn turn)  {
        return true;
    }

    /**
     * @param turn turn in which is checked the validity of this action
     * @return true if this Action can be performed after the Action performedActions, otherwise it returns false. This check
     * is done looking at the type of the performedActions, and if it is contained in the requires of this Action, this Action
     * is valid
     */
    default boolean isValid(FirstTurn turn) {
        return false;
    }

    @Override
    default void handled(Controller controller, User user) throws Exception {
         controller.handleMatchMoveOf (user, this);
    }
}
