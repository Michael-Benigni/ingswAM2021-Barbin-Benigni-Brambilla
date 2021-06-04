package it.polimi.ingsw.client.view.moves;

import it.polimi.ingsw.client.view.ui.cli.IntegerRequest;
import it.polimi.ingsw.client.view.ui.cli.Interlocutor;
import it.polimi.ingsw.client.view.ui.cli.Interpreter;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

public enum WaitingRoomMove implements MoveType {
    START_MATCH("S", startMatchMove ()),
    WAIT_OTHER_PLAYERS("", waitForOtherPlayersMove ()),
    SET_NUM_PLAYERS("P", numPlayersMove ())
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
}