package it.polimi.ingsw.client.view;

import it.polimi.ingsw.utils.network.Channel;

public abstract class View {
    private Channel channel;

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
