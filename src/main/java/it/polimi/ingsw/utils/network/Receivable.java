package it.polimi.ingsw.utils.network;

public interface Receivable<T> {
    default T getInfo() {
        return null;
    }
}
