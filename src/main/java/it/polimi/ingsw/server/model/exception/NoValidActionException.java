package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gamelogic.actions.Action;

public class NoValidActionException extends Exception {
    public NoValidActionException(Action action) {
        super("Your move is not valid. " +
                "\nThe turn can only be of one of this types: " +
                "\n1) Pick resources from the market " +
                "\n2) Buy a development card from the grid " +
                "\n3) Start one or more productions ");
    }
}
