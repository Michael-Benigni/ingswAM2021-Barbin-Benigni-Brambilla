package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;

import java.io.FileNotFoundException;

public class ExistingRoomConnectionCommand implements Command {
    private final int ID;

    public ExistingRoomConnectionCommand(int id) {
        ID = id;
    }

    @Override
    public void handled(Controller controller, User user) throws FileNotFoundException, InvalidUserException, Exception {
        controller.registerToWaitingRoomWith (ID, user);
    }
}
