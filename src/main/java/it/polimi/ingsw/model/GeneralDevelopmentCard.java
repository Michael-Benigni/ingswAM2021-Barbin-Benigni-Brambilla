package it.polimi.ingsw.model;

/**
 * this class models the development card only with its colour and level
 */
public class GeneralDevelopmentCard implements Requirements{
    private final CardColour cardColour;
    private final CardLevel cardLevel;

    /**
     * constructor of the class GeneralDevelopmentCard
     * @param cardColour -> indicates the colour of the development card
     * @param cardLevel -> indicates the level of the development card
     */
    GeneralDevelopmentCard(CardColour cardColour, CardLevel cardLevel) {
        this.cardColour = cardColour;
        this.cardLevel = cardLevel;
    }

    /**
     * this method provides the card colour to the caller
     * @return the card's colour
     */
    protected CardColour getCardColour() {
        return cardColour;
    }

    /**
     * this method provides the card level to the caller
     * @return the card's level
     */
    protected CardLevel getCardLevel() {
        return cardLevel;
    }
}
