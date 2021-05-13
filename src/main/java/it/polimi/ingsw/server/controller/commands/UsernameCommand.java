package it.polimi.ingsw.server.controller.commands;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;

public class UsernameCommand implements Command {
    private String username;

    public UsernameCommand(String username) {
        this.username = username;
    }

    @Override
    public void handled(Controller controller, User user) {
        user.setUsername (username);
    }
}
