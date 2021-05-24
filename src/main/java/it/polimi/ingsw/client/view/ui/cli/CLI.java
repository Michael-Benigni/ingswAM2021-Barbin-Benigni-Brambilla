package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.exceptions.UnavailableMoveName;
import it.polimi.ingsw.client.view.states.Move;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.Sendable;

public class CLI extends UI {
    private final Interpreter interpreter;
    private final Interlocutor interlocutor;

    public CLI() {
        super();
        interpreter = new Interpreter ();
        interlocutor = new Interlocutor();
    }

    @Override
    public void start() {
        new Thread (() -> {
            while (true) {
                interlocutor.write (getState ().menu ());
                String moveAsString = interpreter.listen ();
                Move move = null;
                while (move == null) {
                    try {
                        move = getState ().getMove (moveAsString);
                    } catch (UnavailableMoveName unavailableMoveName) {
                        interlocutor.write (unavailableMoveName.getMessage ());
                    }
                }
                Sendable message = move.ask (interpreter, interlocutor);
                addMessage (message);
            }
        }).start ();
    }

    @Override
    public void notifyError(String info) {
        this.interlocutor.write (info);
    }
}
