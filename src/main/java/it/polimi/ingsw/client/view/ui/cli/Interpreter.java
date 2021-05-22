package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.Scanner;

public class Interpreter {
    private Scanner in;
    private MessageWriter messageWriter;

    public Interpreter() {
        this.in = new Scanner (System.in);
        this.messageWriter = new MessageWriter ();
    }

    public Sendable getTempUserInput() {
        return messageWriter.write ();
    }

    public String listen(Request request) {
        String input = in.nextLine ();
        messageWriter.addProperty (request.getNamePropertyRequested (), request.handleInput (input));
        return input;
    }
}
