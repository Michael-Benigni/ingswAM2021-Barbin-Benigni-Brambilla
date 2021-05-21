package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

import java.util.Objects;

public class QuitMessage extends AbstractMessage<String> {

    public QuitMessage(String message) throws IllegalMessageException {
        super (message, String.class, Header.ToServer.class);
    }

    public static boolean isQuitMessage(String message) {
        QuitMessage msgFromNetwork;
        try {
            msgFromNetwork = new QuitMessage (message);
        } catch (Exception e) {
            return false;
        }
        return Objects.equals (msgFromNetwork.getHeader (), Header.ToServer.QUIT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuitMessage)) return false;
        QuitMessage that = (QuitMessage) o;
        return Objects.equals (getInfo (), that.getInfo ()) && Objects.equals (getHeader (), that.getHeader ());
    }

    @Override
    public int hashCode() {
        return Objects.hash (getInfo ());
    }

    @Override
    public String getInfo() {
        return super.getInfo ();
    }
}
