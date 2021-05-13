package it.polimi.ingsw.server.utils.network;

public class ErrorMessage extends AbstractMessage {
    private final String errorInfo;

    public ErrorMessage(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}
