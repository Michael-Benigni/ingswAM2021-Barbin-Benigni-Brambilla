package it.polimi.ingsw.client.view.ui.cli.states;

import it.polimi.ingsw.client.view.moves.WaitingRoomMove;

public class GameOverState extends ClientState {
    public GameOverState() {
        addAvailableMove (WaitingRoomMove.NEW_MATCH.getCmd (), "START NEW MATCH");
    }

    @Override
    public ClientState getNextState() {
        return new WaitingRoomState();
    }
}
