package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.lightweightmodel.LightweightModel;
import it.polimi.ingsw.utils.network.Channel;

public class View {
    private Channel channel;
    private UI ui;
    private LightweightModel model;

    public View(UI ui) {
        this.ui = ui;
        this.ui.show();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void handle(ToClientMessage message) {

    }
}
