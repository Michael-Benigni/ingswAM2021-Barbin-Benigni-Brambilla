package it.polimi.ingsw.server.model.exception;

public class WrongInitialConfiguration extends Exception {
    public WrongInitialConfiguration() {
        super("Impossible to start the game: the initial conditions aren't satisfied.");
    }
}
