package it.polimi.ingsw.client.view.moves;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.ui.cli.IntegerRequest;
import it.polimi.ingsw.client.view.ui.cli.Interlocutor;
import it.polimi.ingsw.client.view.ui.cli.Interpreter;
import it.polimi.ingsw.client.view.ui.cli.StringRequest;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

public enum WaitingRoomMove implements MoveType {
    START_MATCH("S", startMatchMove ()),
    WAIT_OTHER_PLAYERS("", waitForOtherPlayersMove ()),
    SET_NUM_PLAYERS("P", numPlayersMove ()),
    SET_USERNAME("USER", usernameMove ()),
    CHOOSE_ROOM("ROOM", newOrExistentMatchMove ())
    ;

    private final Move move;
    private final String cmd;

    WaitingRoomMove(String cmd, Move move) {
        this.move = move;
        this.cmd = cmd;
    }

    @Override
    public Move getMove() {
        return this.move;
    }

    @Override
    public String getCmd() {
        return this.cmd;
    }

    private static Move startMatchMove() {
        return (ui)->{
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.START_MATCH);
            return writer.write ();
        };
    }

    private static Move waitForOtherPlayersMove() {
        return (ui) -> null;
    }


    private static Move numPlayersMove() {
        return (ui) -> {
            Interpreter interpreter = ui.getInterpreter ();
            Interlocutor interlocutor = ui.getInterlocutor ();
            IntegerRequest numPlayerRequest = new IntegerRequest ("Set the number of players of the game: ", "dimension");
            MessageWriter writer = numPlayerRequest.handleInput(interlocutor, interpreter, new MessageWriter ());
            writer.setHeader (Header.ToServer.SET_NUM_PLAYERS);
            return writer.write ();
        };
    }

    private static Move newOrExistentMatchMove() {
        return (ui) -> {
            Interpreter interpreter = ui.getInterpreter ();
            Interlocutor interlocutor = ui.getInterlocutor ();
            interlocutor.write ("In which room you want to be added ? \"FIRST FREE\", \"EXISTENT\", \"NEW\"");
            String whichRoomRequest = ui.getInterpreter ().listen ();
            return getMessageForRoomType(whichRoomRequest, interlocutor, interpreter);
        };
    }

    private static Sendable getMessageForRoomType(String whichRoomRequest, Interlocutor interlocutor, Interpreter interpreter) throws IllegalInputException {
        MessageWriter writer = new MessageWriter ();
        switch (whichRoomRequest) {
            case "NEW": {
                writer.setHeader (Header.ToServer.NEW_ROOM);
                break;
            }
            case "EXISTENT": {
                writer.setHeader (Header.ToServer.EXISTING_ROOM);
                IntegerRequest roomIDRequest = new IntegerRequest ("Digit the ID of the room you want to register: ", "ID");
                writer = roomIDRequest.handleInput (interlocutor, interpreter, writer);
                break;
            }
            case "FIRST FREE": {
                writer.setHeader (Header.ToServer.NEW_USER);
                break;
            }
            default:
                throw new IllegalInputException();
        }
        return writer.write ();
    }


    private static Move usernameMove() {
        return (ui) -> {
            Interpreter interpreter = ui.getInterpreter ();
            Interlocutor interlocutor = ui.getInterlocutor ();
            StringRequest usernameReq = new StringRequest("Set your username (if you want to reconnect to an existing game you must set the same username you have used before disconnection): ", "username");
            final int MAX_LENGTH = 12;
            MessageWriter writer = usernameReq.handleInput (interlocutor, interpreter, new MessageWriter (), MAX_LENGTH);
            writer.setHeader (Header.ToServer.SET_USERNAME);
            return writer.write ();
        };
    }
}
