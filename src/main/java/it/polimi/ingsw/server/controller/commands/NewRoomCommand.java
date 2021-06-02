package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;

import java.io.FileNotFoundException;

public class NewRoomCommand implements Command {
    @Override
    public void handled(Controller controller, User user) throws FileNotFoundException, InvalidUserException, Exception {
        controller.registerToNewRoom (user);
    }
}
