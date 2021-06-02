package it.polimi.ingsw.client.view.ui.cli;

public enum Colour {

    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    RESET("\u001B[0m");

    private String escape;

    Colour(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }
}
