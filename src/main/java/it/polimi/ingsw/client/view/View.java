package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.lightweightmodel.LightweightModel;
import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.Channel;
import it.polimi.ingsw.utils.network.JsonTransmittable;
import it.polimi.ingsw.utils.network.ToClientMessage;

public class View {
    private Channel channel;
    private UI ui;
    private LightweightModel model;
    private ClientState currentState;
    private boolean response;

    public View(UI ui) {
        this.ui = ui;
        this.ui.start ();
        this.currentState = new WaitingRoomState ();
    }

    public void loop() {
        new Thread (()->
        {
            while (true) {
                JsonTransmittable transmittable = getNextMove ();
                this.channel.send (transmittable);
                waitForResponse();
            }
        }).start ();
    }

    private synchronized void waitForResponse() {
        while (!this.response) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
    }

    private synchronized void setNextState(ClientState nextState) {
        this.currentState = nextState;
        this.ui.notifyStateChange(nextState);
    }

    public synchronized JsonTransmittable getNextMove() {
        return ui.getUserInput();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    public synchronized void handle(ToClientMessage message) {
        message.getInfo ();
    }

    public synchronized void readyForNextMove() {
        this.response = true;
        this.ui.notifyStateChange (currentState.getNextState());
        this.notifyAll ();
    }
}
