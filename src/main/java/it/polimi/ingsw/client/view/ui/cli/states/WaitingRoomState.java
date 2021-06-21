package it.polimi.ingsw.client.view.ui.cli.states;

import it.polimi.ingsw.client.view.moves.WaitingRoomMove;

public class WaitingRoomState extends ClientState {

    public WaitingRoomState() {
        super();
        addAvailableMove (WaitingRoomMove.START_MATCH.getCmd (), "START MATCH");
        addAvailableMove (WaitingRoomMove.SET_NUM_PLAYERS.getCmd (), "SET NUMBER OF PLAYERS");
        addAvailableMove (WaitingRoomMove.WAIT_OTHER_PLAYERS.getCmd (), "DO NOTHING AND WAIT FOR OTHER PLAYERS");
    }

    @Override
    public ClientState getNextState() {
        return new PlayState();
    }
}
