package it.polimi.ingsw.utils.network.exception;

public class IllegalMessageException extends Exception {
    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return "Illegal message received";
    }
}
