package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.lightweightmodel.LWModel;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.*;

public class Controller {
    private Channel channel;
    private final UI ui;
    private final LWModel model;

    public Controller(UI ui) {
        this.ui = ui;
        this.model = new LWModel ();
    }

    public void loop() {
        new Thread (()->
        {
            try {
                this.ui.start ();
            } catch (Exception e) {
                e.printStackTrace ();
            }
            while (true) {
                Sendable sendable = getNextMove ();
                this.channel.send (sendable);
            }
        }).start ();
    }

    public synchronized void setNextState() {
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

    public LWModel getModel() {
        return model;
    }
}
