package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.TrasmittableWriter;

import java.util.Scanner;

public class Interpreter {
    private Scanner in;
    private TrasmittableWriter trasmittableWriter;

    public Interpreter() {
        this.in = new Scanner (System.in);
        this.trasmittableWriter = new TrasmittableWriter ();
    }

    public String listen() {

        return null;
    }
}
