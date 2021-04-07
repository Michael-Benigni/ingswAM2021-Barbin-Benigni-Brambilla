package it.polimi.ingsw.model.cards.leadercards.cardrequirements;

/**
 * enumeration of the colours that the development card can take on
 */
enum CardColour {
    YELLOW("yellow"), BLUE("blue"), GREEN("green"), PURPLE("purple");

    private final String cardColour;

    /**
     * constructor of the enum class CardColour
     * @param cardColour -> indicates the type of the development card
     */
    CardColour(String cardColour) {
        this.cardColour = cardColour;
    }

}
