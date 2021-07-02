package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;

/**
 * Class that represents the initial command that allows to the leader of the waiting room to start the match.
 */
public class StartMatchCommand implements Command {

    /**
     * Override method of "handled" method of "Command" class. In this case, it triggers the start of the game (into the
     * method "startMatch" will be a check in order to make sure the room is full).
     */
    @Override
    public void handled(Controller controller, User user) throws Exception {
        controller.startMatch (user);
    }
}
