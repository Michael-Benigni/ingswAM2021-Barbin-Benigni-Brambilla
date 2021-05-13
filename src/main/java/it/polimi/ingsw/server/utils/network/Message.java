package it.polimi.ingsw.server.utils.network;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.commands.CommandFactory;
import it.polimi.ingsw.server.utils.config.JsonHandler;

public class Message {
    private Header header;
    private Command command;

    public Message(String messageStr) {
        this.header = (Header) JsonHandler.getAsJavaObjectFromJSONStr (Header.class, "header", messageStr);
        this.command = (Command) JsonHandler.getAsJavaObjectFromJSONStr (CommandFactory.getCommandType(header), "header", messageStr);
    }

    public Command getCommand() {
        return command;
    }
}

