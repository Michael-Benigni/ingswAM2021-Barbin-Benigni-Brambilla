package it.polimi.ingsw.server.model.exception;

public class IncorrectPaymentException extends Exception{
    public IncorrectPaymentException(String message) {
        super(message);
    }
}
