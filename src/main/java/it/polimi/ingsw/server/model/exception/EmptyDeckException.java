package it.polimi.ingsw.server.model.exception;

public class EmptyDeckException extends Exception {

    public EmptyDeckException(Integer rowIndex, Integer colIndex) {
        super("The deck in position (" + rowIndex.toString() + ";" + colIndex.toString() + ") of the grid of development cards is empty");
    }
}
