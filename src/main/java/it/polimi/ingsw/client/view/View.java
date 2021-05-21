package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.lightweightmodel.LightweightModel;
import it.polimi.ingsw.utils.network.Channel;
import it.polimi.ingsw.utils.network.JsonTrasmittable;
import it.polimi.ingsw.utils.network.ToClientMessage;

public class View {
    private Channel channel;
    private UI ui;
    private LightweightModel model;

    public View(UI ui) {
        this.ui = ui;
        this.ui.start ();
    }

    public void loop() {
        while (true) {
            JsonTrasmittable trasmittable = getNextMove ();
            this.channel.send (trasmittable);
            try {
                ui.wait ();
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
    }

    public JsonTrasmittable getNextMove() {
        return ui.getUserInput();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void handle(ToClientMessage message) {
        message.getInfo ().update(model);
    }


    public void readyForNextMove() {
        notify ();
    }
}
