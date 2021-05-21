package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.UI;

public class CLI extends UI {
    private final Interlocutor interlocutor;
    private final Interpreter interpreter;

    public CLI() {
        interpreter = new Interpreter ();
        interlocutor = new Interlocutor ();
    }

    public void start() {
        new Thread (() -> {
            String command = "";
            while (true) {
                //interlocutor.speak (command);
                command = interpreter.listen ();
            }
        }).start ();
    }
}
