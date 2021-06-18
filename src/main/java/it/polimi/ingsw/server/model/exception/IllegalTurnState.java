package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gamelogic.Turn;

public class IllegalTurnState extends Exception {
    public IllegalTurnState() {
        super("This turn has a wrong state.");
    }
}
