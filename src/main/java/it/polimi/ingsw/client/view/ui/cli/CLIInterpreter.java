package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.moves.MoveWrapper;
import it.polimi.ingsw.client.view.exceptions.UnavailableMoveName;
import it.polimi.ingsw.client.view.moves.PlayMove;
import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.moves.WaitingRoomMove;
import it.polimi.ingsw.client.view.ui.Interpreter;

import java.util.*;

public class CLIInterpreter implements Interpreter {
    private Scanner in;
    private ArrayList<MoveWrapper> moves;

    public CLIInterpreter() {
        this.in = new Scanner (System.in);
        initMoves();
    }

    private void initMoves() {
        moves = new ArrayList<> ();
        ArrayList<MoveWrapper> waitingRoomMoves = new ArrayList<> (Arrays.asList (WaitingRoomMove.values ()));
        ArrayList<MoveWrapper> playMoves = new ArrayList<> (Arrays.asList (PlayMove.values ()));
        moves.addAll (waitingRoomMoves);
        moves.addAll (playMoves);
    }

    @Override
    public String listen(String nameProperty) {
        String input;
        while (true) {
            if (in.hasNextLine ()) {
                input = in.nextLine ();
                return input;
            }
        }
    }


    public synchronized Move listenForMove() {
        String input;
        while (true) {
            //if (in.hasNextLine ()) {
                input = in.nextLine ();
                try {
                    return getMove (input);
                } catch (UnavailableMoveName unavailableMoveName) {
                    System.out.printf ("Invalid move for \"%s\"\n", input);
                    listenForMove ();
                }
            //}
        }
    }

    private Move getMove(String input) throws UnavailableMoveName {
        for (MoveWrapper type : moves) {
            if (type.getCmd().equals(input))
                return type.getMove();
        }
        throw new UnavailableMoveName ();
    }
}
