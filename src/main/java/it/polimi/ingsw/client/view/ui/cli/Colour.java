package it.polimi.ingsw.client.view.ui.cli;

public enum Colour {

    RED ("\u001B[38;5;196m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[38;5;11m"),
    BLUE ("\u001B[34m"),
    PURPLE ("\u001B[38;5;93m"),
    GREY("\u001B[38;5;241m"),
    WHITE("\u001B[38;5;255m"),
    RESET("\u001B[0m");

    private String escape;

    Colour(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }
}
