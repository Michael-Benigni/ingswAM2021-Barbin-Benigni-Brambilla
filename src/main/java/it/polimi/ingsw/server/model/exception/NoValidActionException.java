package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gamelogic.actions.Action;

public class NoValidActionException extends Exception {
    public NoValidActionException(Action action) {
        super("The following action isn't considered valid: " + action.toString());
    }
}
