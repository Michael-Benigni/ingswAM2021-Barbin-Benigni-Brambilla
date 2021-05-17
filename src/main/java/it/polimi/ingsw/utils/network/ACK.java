package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.config.JsonHandler;

import java.util.Objects;

public class ACK extends AbstractMessage<Integer> {
    private int seqNumber;
    private transient static int numOfACKs = 0;

    public ACK() {
        super (Header.ACK);
        seqNumber = setSequentialNum ();
    }

    public boolean isTheSameACK(String fromNetwork) {
        ACK ackFromNetwork = null;
        try {
            ackFromNetwork = (ACK) JsonHandler.fromJson (fromNetwork, ACK.class);
        } catch (Exception e) {
            seqNumber = -1;
        }
        return this.equals (ackFromNetwork);
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

    @Override
    public Integer getInfo() {
        return this.seqNumber;
    }
}
