package it.polimi.ingsw.server.model.exception;

public class InvalidMarketRowException extends Exception {
    public InvalidMarketRowException(Integer selectedRowIndex, Integer maxRows) {
        super("Wrong market row: " + selectedRowIndex.toString() + " out of " + maxRows.toString() + ".");
    }
}
