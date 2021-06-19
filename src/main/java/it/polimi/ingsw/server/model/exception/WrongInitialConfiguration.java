package it.polimi.ingsw.server.model.exception;

public class WrongInitialConfiguration extends Exception {
    public WrongInitialConfiguration() {
        super("Impossible to start the game: the initial conditions aren't satisfied." +
                "\nCheck if you have taken the right number of initial resources" +
                "\nor if you have discarded the right number of leader cards.");
    }
}
