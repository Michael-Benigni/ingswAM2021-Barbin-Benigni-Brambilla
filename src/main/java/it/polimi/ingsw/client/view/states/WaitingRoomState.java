package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.moves.WaitingRoomMove;

public class WaitingRoomState extends ClientState {

    public WaitingRoomState() {
        super();
        addAvailableMove (WaitingRoomMove.SET_NUM_PLAYERS.getCmd (), "SET NUMBER OF PLAYERS");
        addAvailableMove (WaitingRoomMove.WAIT_OTHER_PLAYERS.getCmd (), "WAIT FOR OTHER PLAYERS");
        addAvailableMove (WaitingRoomMove.START_MATCH.getCmd (), "START MATCH");
    }

    @Override
    public ClientState getNextState() {
        return new PlayState();
    }
}
