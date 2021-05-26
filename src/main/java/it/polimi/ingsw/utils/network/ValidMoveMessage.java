package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

import java.util.Objects;

public class ValidMoveMessage extends AbstractMessage<String> {

    public ValidMoveMessage() {
        super (Header.Common.VALID, "Valid move");
    }

    protected ValidMoveMessage(String msg) throws IllegalMessageException {
        super (msg, String.class, Header.Common.class);
    }

    public static boolean isValidMoveMessage(String msg) {
        ValidMoveMessage moveMessage;
        try {
            moveMessage = new ValidMoveMessage (msg);
        } catch (IllegalMessageException e) {
            return false;
        }
        return Objects.equals (moveMessage.getHeader (), Header.Common.VALID);
    }
}
