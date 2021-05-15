package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.server.controller.commands.Command;

public interface ReceivableHandler {
    void onReceived(Receivable<Command> receivable) throws Exception;
}
