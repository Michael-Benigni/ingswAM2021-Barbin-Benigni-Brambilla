package it.polimi.ingsw.server.controller.exception;

public class ImpossibleChangingSizeException extends Exception {

    public ImpossibleChangingSizeException() {
        super("Impossible to change the number of players in this waiting room.");
    }
}
