package it.polimi.ingsw.utils.network;

public class ErrorMessage extends AbstractMessage<String> {
    private final String errorInfo;

    public ErrorMessage(String errorInfo) {
        super(ToServer.ERROR);
        this.errorInfo = errorInfo;
    }

    @Override
    public String getInfo() {
        return this.errorInfo;
    }
}
