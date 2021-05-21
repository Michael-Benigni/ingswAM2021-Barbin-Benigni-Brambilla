package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.ui.cli.Request;

public class WaitingRoomState extends ClientState {
    @Override
    public String getOptions() {
        return null;
    }

    @Override
    public Request nextRequest(String input) {
        return null;
    }

    @Override
    public ClientState getNextState() {
        return null;
    }
}
