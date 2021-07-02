package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;

/**
 * Class that represents the initial command that allows to an user to set its username.
 */
public class UsernameCommand implements Command {
    private String username;

    /**
     * Constructor method of this class.
     */
    public UsernameCommand(String username) {
        this.username = username;
    }

    /**
     * Override method of "handled" method of "Command" class. In this case, it updates the username of the provided user.
     */
    @Override
    public void handled(Controller controller, User user) {
        user.setUsername (username);
    }
}
