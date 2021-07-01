package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.updates.UpdateFactory;
import it.polimi.ingsw.client.view.updates.ViewUpdate;
import it.polimi.ingsw.utils.config.JsonHandler;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

public class ToClientMessage extends AbstractMessage<ViewUpdate>{
    private final Integer progressiveNumber;

    public ToClientMessage(String msg) throws IllegalMessageException {
        super (msg, UpdateFactory.getUpdateType ((Header.ToClient) parseForHeader (msg, Header.ToClient.class)), Header.ToClient.class);
        this.progressiveNumber = parseForProgressiveNumber (msg);
    }

    private int parseForProgressiveNumber(String msg) throws IllegalMessageException {
        try {
            return (Integer) JsonHandler.getAsJavaObjectFromJSONStr (Integer.class, "progressiveNumber", msg);
        } catch (Exception e) {
            throw new IllegalMessageException ();
        }
    }

    public Integer getProgressiveNumber() {
        return progressiveNumber;
    }

    public static Integer maxValueProgrNum() {
        return ClientPrefs.getMaxProgressiveNumber();
    }
}
