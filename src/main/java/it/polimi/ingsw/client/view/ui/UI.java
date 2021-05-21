package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.utils.network.JsonTransmittable;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class UI {
    private Queue<JsonTransmittable> userInputs;
    private ClientState state;

    public UI () {
        this.userInputs = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
    }

    public abstract void start();

    public synchronized JsonTransmittable getUserInput() {
        while (this.userInputs.isEmpty ()) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        return this.userInputs.remove ();
    }

    public synchronized ClientState getState() {
        return state;
    }

    protected synchronized void addUserInput(JsonTransmittable transmittable) {
        this.userInputs.add (transmittable);
        try {
            wait ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }

    public void notifyStateChange(ClientState state) {
        state.getOptions ();
    }
}
