package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.network.exception.IllegalMessageException;
import java.util.Objects;

public class ACK extends AbstractMessage<Integer> {

    private transient static int numOfACKs = 0;

    public ACK() {
        super (Header.Common.ACK, setSequentialNum ());
    }

    public ACK(String jsonString) throws IllegalMessageException {
        super(jsonString, Integer.class, Header.Common.class);
    }

    private static int setSequentialNum() {
        numOfACKs++;
        return numOfACKs;
    }

    public static boolean isACKMessage(String jsonString) {
        ACK ack;
        try {
            ack = new ACK(jsonString);
        } catch (IllegalMessageException e) {
            return false;
        }
        return Objects.equals (ack.getHeader (), Header.Common.ACK);
    }

    public boolean isTheSameACK(String fromNetwork) {
        ACK ackFromNetwork;
        try {
            ackFromNetwork = new ACK (fromNetwork);
        } catch (Exception e) {
            return false;
        }
        return this.equals (ackFromNetwork);
    }

    @Override
    public int hashCode() {
        return super.hashCode ();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals (obj);
    }
}
