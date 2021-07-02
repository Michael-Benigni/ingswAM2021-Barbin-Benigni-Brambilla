package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.EmptyDeckException;

import java.io.FileNotFoundException;

/**
 * Class that represents the initial command that allows to a new user to register itself.
 */
public class NewUserCommand implements Command {

    /**
     * Override of "handled" method of "Command" class. In this case, it starts the registration of a new user.
     */
    @Override
    public void handled(Controller controller, User user) throws FileNotFoundException, InvalidUserException, EmptyDeckException {
        controller.register (user);
    }
}
