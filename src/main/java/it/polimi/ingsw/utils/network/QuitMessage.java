package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.config.JsonHandler;

import java.util.Objects;

public class QuitMessage extends AbstractMessage<String> {
    private final String info = "quit";

    QuitMessage() {
        super (ToServer.QUIT);
    }

    public static boolean isQuitMessage(String message) {
        QuitMessage msgFromNetwork;
        try {
            msgFromNetwork = (QuitMessage) JsonHandler.fromJson (message, QuitMessage.class);
        } catch (Exception e) {
            return false;
        }
        if (msgFromNetwork.getMessage().equals (message))
            return true;
        return false;
    }

    private String getMessage() {
        return this.info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuitMessage)) return false;
        QuitMessage that = (QuitMessage) o;
        return Objects.equals (info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash (info);
    }

    @Override
    public String getInfo() {
        return this.info;
    }
}
