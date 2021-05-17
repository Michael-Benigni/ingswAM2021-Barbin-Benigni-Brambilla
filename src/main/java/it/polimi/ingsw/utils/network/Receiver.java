package it.polimi.ingsw.utils.network;

public interface Receiver {
    void onReceived(String receivable) throws Exception;
}
