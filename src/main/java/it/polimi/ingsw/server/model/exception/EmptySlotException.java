package it.polimi.ingsw.server.model.exception;

public class EmptySlotException extends Exception{
    public EmptySlotException() {
        super("This slot of development cards is empty");
    }
}
