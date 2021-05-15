package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.config.JsonHandler;

import java.util.Objects;

public class ACK extends AbstractMessage {
    private int seqNumber;
    private transient static int numOfACKs = 0;

    public ACK() {
        seqNumber = setSequentialNum ();
    }

    public ACK(String fromNetwork) {
        try {
            ACK ackFromNetwork = (ACK) JsonHandler.fromJson (fromNetwork, ACK.class);
            seqNumber = ackFromNetwork.seqNumber;
        } catch (Exception e) {
            seqNumber = -1;
        }
    }

    private static int setSequentialNum() {
        numOfACKs++;
        return numOfACKs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ACK)) return false;
        ACK ack = (ACK) o;
        return seqNumber == ack.seqNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash (seqNumber);
    }
}
