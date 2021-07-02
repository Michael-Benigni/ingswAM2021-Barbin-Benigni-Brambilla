package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;

/**
 * Class that represents the initial command that allows to an user to enter in an existent waiting room with a provided ID.
 */
public class ExistingRoomConnectionCommand implements Command {
    /**
     * Identification number of the waiting room you want to be connected.
     */
    private final int ID;

    /**
     * Constructor method of this class.
     */
    public ExistingRoomConnectionCommand(int id) {
        ID = id;
    }

    /**
     * Override method of "handled" method of "Command" class. In this case it starts the registration of the provided
     * user in the waiting room with the ID provided to the constructor.
     */
    @Override
    public void handled(Controller controller, User user) throws Exception {
        controller.registerToWaitingRoomWith (ID, user);
    }
}
