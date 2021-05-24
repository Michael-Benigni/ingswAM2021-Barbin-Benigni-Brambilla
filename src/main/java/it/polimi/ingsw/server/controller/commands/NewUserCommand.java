package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.EmptyDeckException;

import java.io.FileNotFoundException;

public class NewUserCommand implements Command {
    @Override
    public void handled(Controller controller, User user) throws FileNotFoundException, InvalidUserException, EmptyDeckException {
        controller.register (user);
    }
}
