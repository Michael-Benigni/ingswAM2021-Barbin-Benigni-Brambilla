package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.ui.UI;

public class CLI extends UI {
    private final Interpreter interpreter;
    private final Interlocutor interlocutor;

    public CLI() {
        interpreter = new Interpreter ();
        interlocutor = new Interlocutor();
    }

    @Override
    public void start() {
        new Thread (() -> {
            String inputAsString = "";
            while (!getState ().isEnded()) {
                Request nextRequest = getState ().nextRequest(inputAsString);
                interlocutor.write (nextRequest.getRequestDesc ());
                inputAsString = interpreter.listen (nextRequest);
            }
            addUserInput (this.interpreter.getTempUserInput ());
        }).start ();
    }
}
