package it.polimi.ingsw.client.view.ui.cli;

public enum Colour {

    RED ("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE ("\u001B[34m"),
    PURPLE ("\u001B[35m"),
    GREY(""),
    WHITE(""),
    RESET("\u001B[0m");

    private String escape;

    Colour(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }
}
