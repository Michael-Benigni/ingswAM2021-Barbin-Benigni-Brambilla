package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.UserInput;

import java.util.Scanner;

public class Interpreter {
    private Scanner in;
    private UserInput userInput;

    private UserInput tempUserInput;

    public Interpreter() {
        this.in = new Scanner (System.in);
        this.userInput = new UserInput ();
        this.tempUserInput = new UserInput ();
    }

    public UserInput getTempUserInput() {
        return tempUserInput;
    }

    public String listen(Request request) {
        String input = in.nextLine ();
        tempUserInput.addProperty (request.getNamePropertyRequested (), request.handleInput (input));
        return input;
    }
}
