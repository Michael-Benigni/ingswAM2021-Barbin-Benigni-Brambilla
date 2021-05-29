package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.utils.network.Channel;

public abstract class AbstractView {

    private final Channel channel;

    public AbstractView(Channel channel) {
        this.channel = channel;
        this.channel.setView (this);
    }

    protected Channel getChannel() {
        return channel;
    }

    public abstract void disconnect() throws InvalidUserException;
}
