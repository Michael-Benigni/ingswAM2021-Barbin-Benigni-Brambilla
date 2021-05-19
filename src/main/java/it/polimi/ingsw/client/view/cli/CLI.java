package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.UI;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI extends UI {
    private final Scanner input;
    private final PrintWriter output;

    public CLI() {
        input = new Scanner (System.in);
        output = new PrintWriter (System.out);
    }
}
