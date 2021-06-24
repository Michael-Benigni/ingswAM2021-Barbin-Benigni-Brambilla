package it.polimi.ingsw.utils;

public interface Attachable<T> {


    /**
     * This method is used to attach the attached to the object that implements this interface
     * @param attached
     */
    void attach(T attached);
}
