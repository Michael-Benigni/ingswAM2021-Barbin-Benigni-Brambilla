package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.moves.Move;
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
            while (true) {
                Move move = interpreter.listenForMove ();
                actuateMove (move);
                clear ();
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

    private void actuateMove(Move move) {
        Sendable message = move.ask (interpreter, interlocutor);
        if (message != null) {
            addMessage (message);
        }
    }

    private void printMenu() {
        interlocutor.write (getState ().menu ());
    }

    private void registration() {
        addMessage (usernameMove ().ask (interpreter, interlocutor));
        addMessage (newUserMove ().ask (interpreter, interlocutor));
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

    private void clear() {
        for (int i = 0; i < 40; i++) {
            interlocutor.write ("\n");
        }
    }


    @Override
    public void notifyError(String info) {
        this.interlocutor.write ("Error: " + info);
        this.nextInputRequest ();
    }

    @Override
    public void notifyMessage(String info) {
        this.interlocutor.write ("From Server: " + info);
    }

    @Override
    public void nextInputRequest() {
        printMenu ();
        this.interlocutor.write ("Digit a new command: ");
    }

    @Override
    public void setNextState() {
        super.setNextState ();
        clear ();
        printMenu ();
    }
}
