package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.exceptions.UnavailableMoveName;
import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.Move;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
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
            registration();
            ClientState triggeredState = getState ();
            interlocutor.write (getState ().menu ());
            while (true) {
                //TODO check if the triggeredState changes (without left assignment)
                printMenuIfNewState(triggeredState);
                String moveAsString = interpreter.listen ();
                actuateMove (moveAsString);
                waitUntilReady ();
            }
        }).start ();
    }

    @Override
    public void showPersonalBoard() {

    }

    @Override
    public void showGameBoard() {

    }

    @Override
    public void showInfoGame() {

    }

    private void actuateMove(String moveAsString) {
        Move move = null;
        try {
            move = getState ().getMove (moveAsString);
            if (move == null) {
                actuateMove (this.interpreter.listen ());
                return;
            }
        } catch (UnavailableMoveName unavailableMoveName) {
            interlocutor.write (unavailableMoveName.getMessage ());
            actuateMove (this.interpreter.listen ());
        }
        Sendable message = move.ask (interpreter, interlocutor);
        if (message != null) {
            addMessage (message);
        }
    }

    private ClientState printMenuIfNewState(ClientState state) {
        if (!state.equals (getState ())) {
            interlocutor.write (getState ().menu ());
            state = getState ();
        }
        else
            interlocutor.write ("Digit a new command: ");
        return state;
    }

    private void registration() {
        addMessage (usernameMove ().ask (interpreter, interlocutor));
        addMessage (newUserMove ().ask (interpreter, interlocutor));
        ready (false);
        waitUntilReady();
    }

    private synchronized void waitUntilReady() {
        while (!isReadyForNextMove ()) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
    }

    private Move newUserMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.NEW_USER);
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
    public void notifyError(String info) {
        this.interlocutor.write ("Error: " + info);
    }

    @Override
    public void notifyMessage(String info) {
        this.interlocutor.write ("From Server: " + info);
    }
}
