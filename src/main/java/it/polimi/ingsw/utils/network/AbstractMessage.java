package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.config.JsonHandler;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

import java.util.Objects;


public class AbstractMessage<T> implements JsonTrasmittable, Receivable<T>{
    private Header header;
    private T info;

    public AbstractMessage(Header header, T info) {
        this.header = header;
        this.info = info;
    }

    protected AbstractMessage(String messageStr, Class<? extends Header> headerType) throws IllegalMessageException {
        this.header = parseForHeader (messageStr, headerType);
    }

    protected AbstractMessage(String messageStr, Class<? extends T> infoClass, Class<? extends Header> headerType) throws IllegalMessageException {
        this.header = parseForHeader (messageStr, headerType);
        this.info = parseForInfo (messageStr, infoClass);
    }

    protected static Header parseForHeader(String messageStr, Class<? extends Header> headerType) throws IllegalMessageException {
        try {
            return (Header) JsonHandler.getAsJavaObjectFromJSONStr (headerType, "header/", messageStr);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    protected T parseForInfo(String messageStr, Class<? extends T> infoClass) throws IllegalMessageException {
        try {
            return (T) JsonHandler.getAsJavaObjectFromJSONStr (infoClass, "info", messageStr);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    protected Header getHeader() {
        return header;
    }

    public T getInfo() {
        return this.info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractMessage)) return false;
        AbstractMessage<?> message = (AbstractMessage<?>) o;
        return getHeader ().equals (message.getHeader ()) && getInfo ().equals (message.getInfo ());
    }

    @Override
    public int hashCode() {
        return Objects.hash (getHeader (), getInfo ());
    }
}
