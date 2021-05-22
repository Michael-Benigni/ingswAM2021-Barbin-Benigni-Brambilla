package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.ui.cli.IntegerRequest;
import it.polimi.ingsw.client.view.ui.cli.Request;
import it.polimi.ingsw.client.view.ui.cli.StringRequest;

public class WaitingRoomState extends ClientState {

    public WaitingRoomState() {
        super ();
        addRequests (new StringRequest ("Set your username: ", "username"));
        addRequests (new IntegerRequest ("Set the number of players in the match: ", "numOfPlayer"));

    }

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
        return new PlayState();
    }
}
