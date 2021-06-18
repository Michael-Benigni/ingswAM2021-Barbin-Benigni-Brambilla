package it.polimi.ingsw.server.model.exception;

public class InvalidMarketColumnException extends Exception {
    public InvalidMarketColumnException(Integer selectedColumnIndex, Integer maxColumns) {
        super("Wrong market column: " + selectedColumnIndex.toString() + " out of " + maxColumns.toString() + ".");
    }
}
