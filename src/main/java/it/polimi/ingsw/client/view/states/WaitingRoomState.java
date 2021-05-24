package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.ui.cli.IntegerRequest;
import it.polimi.ingsw.client.view.ui.cli.StringRequest;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

public class WaitingRoomState extends ClientState {

    public WaitingRoomState() {
        super();
        addAvailableMove ("N", usernameMove (), "SET USERNAME");
        addAvailableMove ("P", numPlayersMove (), "SET NUMBER OF PLAYERS");
        addAvailableMove ("U", newUserMove (), "REGISTER AS NEW USER");
    }

    private Move newUserMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.NEW_USER);
            return writer.write ();
        };
    }

    private Move numPlayersMove() {
        return (interpreter, interlocutor) -> {
            IntegerRequest numPlayerRequest = new IntegerRequest ("Set the number of players of the game: ", "numOfPlayer");
            MessageWriter writer = numPlayerRequest.handleInput(interlocutor, interpreter, new MessageWriter ());
            writer.setHeader (Header.ToServer.SET_NUM_PLAYERS);
            return writer.write ();
        };
    }

    private Move usernameMove() {
        return (interpreter, interlocutor) -> {
            StringRequest usernameReq = new StringRequest("Set your username: ", "username");
            MessageWriter writer = usernameReq.handleInput (interlocutor, interpreter, new MessageWriter ());
            writer.setHeader (Header.ToServer.SET_USERNAME);
            return writer.write ();
        };
    }


    @Override
    public ClientState getNextState() {
        return new PlayState();
    }
}
