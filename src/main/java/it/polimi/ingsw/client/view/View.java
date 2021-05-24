package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.lightweightmodel.LightweightModel;
import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.*;

public class View {
    private Channel channel;
    private UI ui;
    private LightweightModel model;
    private ClientState currentState;
    private boolean response;

    public View(UI ui) {
        this.ui = ui;
        this.currentState = new WaitingRoomState ();
    }

    public void loop() {
        new Thread (()->
        {
            this.ui.start ();
            while (true) {
                Sendable sendable = getNextMove ();
                this.channel.send (sendable);
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
        this.response = false;
    }

    private synchronized void setNextState(ClientState nextState) {
        this.currentState = nextState;
        this.ui.setNextState ();
    }

    public synchronized Sendable getNextMove() {
        return ui.getNextMessage ();
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }


    public synchronized void handle(ToClientMessage message) {
        message.getInfo ();
    }

    public synchronized void readyForNextMove() {
        this.response = true;
        this.notifyAll ();
    }

    public void handleError(ErrorMessage errorMessage) {
        ui.notifyError(errorMessage.getInfo ());
    }
}
