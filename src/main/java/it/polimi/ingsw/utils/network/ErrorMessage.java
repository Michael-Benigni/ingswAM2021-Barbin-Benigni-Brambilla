package it.polimi.ingsw.utils.network;

public class ErrorMessage extends AbstractMessage {
    private final String errorInfo;

    public ErrorMessage(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
