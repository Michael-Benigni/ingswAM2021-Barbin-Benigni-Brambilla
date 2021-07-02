package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;

/**
 * Class that represents the initial command that allows to an user to create an new waiting room.
 */
public class NewRoomCommand implements Command {

    /**
     * Override method of "handled" method of "Command" class. In this case, it starts the creation of a new waiting room
     * and the registration of the provided user in that room.
     */
    @Override
    public void handled(Controller controller, User user) throws Exception {
        controller.registerToNewRoom (user);
    }
}
