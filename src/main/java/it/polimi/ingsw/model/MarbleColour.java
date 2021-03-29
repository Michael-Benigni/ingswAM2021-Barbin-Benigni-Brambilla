package it.polimi.ingsw.model;

/**
 * enumeration of the colours that the marbles can take on
 */
public enum MarbleColour {
    WHITE("white"), RED("red"), YELLOW("yellow"),
    BLUE("blue"), GREY("grey"), PURPLE("purple");

    private final String marbleColour;

    /**
     * constructor of the enum class MarbleColour
     * @param marbleColour -> it is the label that indicates the colour of the marble
     */
    MarbleColour(String marbleColour) {
        this.marbleColour = marbleColour;
    }
}
