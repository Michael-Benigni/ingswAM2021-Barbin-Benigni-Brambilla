package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.lightweightmodel.LightweightModel;
import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.*;

import java.util.NoSuchElementException;

public class View {
    private Channel channel;
    private UI ui;
    private LightweightModel model;

    public View(UI ui) {
        this.ui = ui;
        this.model = new LightweightModel ();
    }

    public void loop() {
        new Thread (()->
        {
            this.ui.start ();
            while (true) {
                Sendable sendable = getNextMove ();
                this.channel.send (sendable);
            }
        }).start ();
    }

    private synchronized void setNextState() {
        this.ui.setNextState ();
    }

    private Sendable getNextMove() {
        return this.getUI ().getNextMessage ();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public synchronized void handle(ToClientMessage message) {
        message.getInfo ().update (this);
    }

    public void handleError(ErrorMessage errorMessage) {
        ui.notifyError(errorMessage.getInfo ());
    }

    public UI getUI() {
        return this.ui;
    }

    public LightweightModel getModel() {
        return model;
    }
}
