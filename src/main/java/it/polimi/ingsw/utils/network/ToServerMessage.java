package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.commands.CommandFactory;
import it.polimi.ingsw.utils.config.JsonHandler;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ToServerMessage extends AbstractMessage {
    private ToServer toServer;
    private Command command;

    public ToServerMessage(String messageStr) throws IllegalMessageException {
        super (messageStr);
        try {
            this.toServer = (ToServer) JsonHandler.getAsJavaObjectFromJSONStr (ToServer.class, "toServer", messageStr);
            Class<? extends Command> commandClass = CommandFactory.getCommandType (toServer);
            this.command = (Command) JsonHandler.getAsJavaObjectFromJSONStr (commandClass, "command", messageStr);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    public ToServerMessage(ToServer toServer, Command command) {
        super (toServer);
        this.command = command;
    }

    public Command getInfo() {
        return this.command;
    }
}

