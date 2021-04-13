package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.model.cards.leadercards.Requirements;

/**
 * this class models the development card only with its colour and level
 */
class GeneralDevelopmentCard implements Requirements {
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

    /**
     * this method return true if the two compared cards have the same level
     * @param cardToCompare
     * @return
     */
    boolean hasSameLevel(DevelopmentCard cardToCompare) {
        if(this.cardLevel.compareTo(cardToCompare.getCardLevel()) == 0)
            return true;
        return false;
    }

    /**
     * this method return true if the two compared cards have the same colour
     * @param cardToCompare
     * @return
     */
    boolean hasSameColour(DevelopmentCard cardToCompare) {
        if (this.cardColour.compareTo(cardToCompare.getCardColour()) == 0)
            return true;
        return false;
    }

    /** method that verify if the level of the caller card is higher by one than the card passed in parameters
     * @param card
     * @return true if the condition specified is verified
     */
    boolean isOfNextLevel(DevelopmentCard card) {
        boolean result = false;
        try {
            if (this.getCardLevel().ordinal() == card.getCardLevel().ordinal() + 1)
                result = true;
        } catch (NullPointerException e) {
            result = isOfFirstLevel();
        }
        return result;
    }

    /**
     * method that verify if the level of the caller card is the first
     * @return true if the level of the caller card is the first
     */
    private boolean isOfFirstLevel() {
        if (this.getCardLevel().equals(CardLevel.ONE))
            return true;
        return false;
    }

}
