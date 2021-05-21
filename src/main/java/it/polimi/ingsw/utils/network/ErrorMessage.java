package it.polimi.ingsw.utils.network;

public class ErrorMessage extends AbstractMessage<String> {

    public ErrorMessage(String errorInfo) {
        super(Header.Common.ERROR, errorInfo);
    }
}
