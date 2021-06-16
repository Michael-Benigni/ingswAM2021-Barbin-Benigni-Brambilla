package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

import java.util.Objects;

public class ErrorMessage extends AbstractMessage<String> {

    public ErrorMessage(Exception e) {
        super(Header.Common.ERROR, e.getMessage ());
    }

    public ErrorMessage(String msg) throws IllegalMessageException {
        super(msg, String.class, Header.Common.class);
    }

    public static boolean isErrorMessage(String msg) {
        ErrorMessage errorMessage;
        try {
            errorMessage = new ErrorMessage (msg);
        } catch (IllegalMessageException e) {
            return false;
        }
        return Objects.equals (errorMessage.getHeader (), Header.Common.ERROR);
    }
}
