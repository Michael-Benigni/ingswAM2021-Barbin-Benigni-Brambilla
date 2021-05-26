package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.ui.cli.IntegerRequest;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

public class WaitingRoomState extends ClientState {

    public WaitingRoomState() {
        super();
        addAvailableMove ("P", numPlayersMove (), "SET NUMBER OF PLAYERS");
        addAvailableMove ("W", waitForOtherPlayersMove (), "WAIT FOR OTHER PLAYERS");
        addAvailableMove ("S", startMatchMove(), "START MATCH");
    }

    private Move startMatchMove() {
        return (interpreter, interlocutor)->{
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.START_MATCH);
            return writer.write ();
        };
    }

    private Move waitForOtherPlayersMove() {
        return null;
    }


    private Move numPlayersMove() {
        return (interpreter, interlocutor) -> {
            IntegerRequest numPlayerRequest = new IntegerRequest ("Set the number of players of the game: ", "dimension");
            MessageWriter writer = numPlayerRequest.handleInput(interlocutor, interpreter, new MessageWriter ());
            writer.setHeader (Header.ToServer.SET_NUM_PLAYERS);
            return writer.write ();
        };
    }


    @Override
    public ClientState getNextState() {
        return new PlayState();
    }
}
