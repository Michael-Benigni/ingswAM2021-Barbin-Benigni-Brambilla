package it.polimi.ingsw.client.view.ui.cli;

import java.io.PrintWriter;

public class Interlocutor {
    private final PrintWriter writer;

    public Interlocutor() {
        writer = new PrintWriter (System.out);
    }

    public void write(String string) {
        System.out.printf ("%s\n", string);
    }
}
