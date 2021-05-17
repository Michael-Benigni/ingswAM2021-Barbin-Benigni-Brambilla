package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.commands.CommandFactory;
import it.polimi.ingsw.utils.config.JsonHandler;
import it.polimi.ingsw.utils.network.AbstractMessage;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ServerMessage extends AbstractMessage<Command> {
    private Command command;

    public ServerMessage(String messageStr) throws IllegalMessageException {
            super(messageStr);
        try {
            Class<? extends Command> commandClass = CommandFactory.getCommandType (getHeader());
            this.command = (Command) JsonHandler.getAsJavaObjectFromJSONStr (commandClass, "command", messageStr);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    public ServerMessage(Header header, Command command) {
        super(header);
        this.command = command;
    }

    @Override
    public Command getInfo() {
        return this.command;
    }
}

