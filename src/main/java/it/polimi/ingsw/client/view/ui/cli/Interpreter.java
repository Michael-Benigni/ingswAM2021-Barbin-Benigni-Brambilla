package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.Scanner;

public class Interpreter {
    private Scanner in;
    private MessageWriter messageWriter;

    public Interpreter() {
        this.in = new Scanner (System.in);
        this.messageWriter = new MessageWriter ();
    }

    public synchronized String listen() {
        String input;
        while (true) {
            if (in.hasNextLine ()) {
                input = in.nextLine ();
                    return input;
            }
        }
    }
}
