package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.config.JsonHandler;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;


public class AbstractMessage<T> implements JsonTrasmittable, Receivable<T>{
    private Header header;

    protected AbstractMessage(Header header) {
        this.header = header;
    }

    protected AbstractMessage(String messageStr) throws IllegalMessageException {
        this.header = parseForHeader (messageStr);
    }

    private static Header parseForHeader(String messageStr) throws IllegalMessageException {
        try {
            return (Header) JsonHandler.getAsJavaObjectFromJSONStr (Header.class, "header", messageStr);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    protected Header getHeader() {
        return header;
    }
}
