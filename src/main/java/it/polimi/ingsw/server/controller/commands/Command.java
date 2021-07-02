package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;

/**
 * Interface that groups all the action that an user can execute before the start of the match.
 */
public interface Command {
    /**
     * Method that start the execution of this command.
     */
    default void handled(Controller controller, User user) throws Exception {
    }
}
