package it.polimi.ingsw.utils.network;

public class ValidMoveMessage extends AbstractMessage<String> {

    protected ValidMoveMessage() {
        super (Header.Common.VALID, "Valid move");
    }

    public static boolean isValidMoveMessage(String msg) {
        //TODO:
        return false;
    }
}
