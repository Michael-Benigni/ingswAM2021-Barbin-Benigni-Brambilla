package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.commands.CommandFactory;
import it.polimi.ingsw.utils.config.JsonHandler;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class Message extends AbstractMessage {
    private Header header;
    private Command command;

    public Message(String messageStr) throws IllegalMessageException {
        try {
            this.header = (Header) JsonHandler.getAsJavaObjectFromJSONStr (Header.class, "header", messageStr);
            Class<? extends Command> commandClass = CommandFactory.getCommandType (header);
            this.command = (Command) JsonHandler.getAsJavaObjectFromJSONStr (commandClass, "command", messageStr);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    public Message(Header header, Command command) {
        this.header = header;
        this.command = command;
    }

    public Command getInfo() {
        return this.command;
    }
}

