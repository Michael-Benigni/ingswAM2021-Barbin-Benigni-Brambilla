package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.commands.CommandFactory;
import it.polimi.ingsw.server.controller.commands.NewUserCommand;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ToServerMessage extends AbstractMessage<Command> {

    public ToServerMessage(String messageStr) throws IllegalMessageException {
        super (messageStr, CommandFactory.getCommandType ((Header.ToServer) parseForHeader (messageStr, Header.ToServer.class)), Header.ToServer.class);
    }
}

