package it.polimi.ingsw.server.model.cards.developmentcards;

import it.polimi.ingsw.server.exception.EmptySlotException;
import it.polimi.ingsw.server.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.cards.leadercards.Requirement;

import java.util.ArrayList;

/**
 * this class models the development
 * card only with its colour and level
 */
public class GeneralDevelopmentCard implements Requirement {
    private final CardColour cardColour;
    private final CardLevel cardLevel;

    /**
     * constructor of the class GeneralDevelopmentCard
     * @param cardColour -> indicates the colour of the development card
     * @param cardLevel -> indicates the level of the development card
     */
    public GeneralDevelopmentCard(CardColour cardColour, CardLevel cardLevel) {
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
     * this method return true if the two
     * compared cards have the same level.
     * @param cardToCompare -> the card we want to compare
     * @return -> boolean value: true if the two cards have the same level
     *                           false if the two cards haven't the same level
     */
    //TODO: make it private
    boolean hasSameLevel(GeneralDevelopmentCard cardToCompare) {
        if(this.cardLevel == null || cardToCompare.getCardLevel() == null)
            return true;
        if(this.cardLevel.compareTo(cardToCompare.getCardLevel()) == 0)
            return true;
        return false;
    }

    /**
     * this method return true if the two
     * compared cards have the same colour.
     * @param cardToCompare -> the card we want to compare
     * @return -> boolean value: true if the two cards have the same level
     *                           false if the two cards haven't the same level
     */
    boolean hasSameColour(GeneralDevelopmentCard cardToCompare) {
        if(this.cardColour == null || cardToCompare.getCardColour() == null)
            return true;
        if (this.cardColour.compareTo(cardToCompare.getCardColour()) == 0)
            return true;
        return false;
    }

    /**
     * method that verify if the level of the caller
     * card is higher by one than the card passed in parameters.
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

    /**
     * method that overrides the
     * "Requirement" method "containedIn".
     * it checks if the player satisfies
     * the requirements to play a leader card
     * @param player -> player we want to examine
     * @return -> boolean value: true if the player satisfies this requirements
     *                           false if the player doesn't satisfy this requirements
     * @throws EmptySlotException
     * @throws WrongSlotDevelopmentIndexException
     */
    @Override
    public boolean containedIn(Player player) throws EmptySlotException, WrongSlotDevelopmentIndexException {
        ArrayList <DevelopmentCard> requirements = player.getDevCardRequirements();
        for(int i = 0; i < requirements.size(); i++) {
            if(this.hasSameColour(requirements.get(i)) && this.hasSameLevel(requirements.get(i))) {
                return true;
            }
        }
        return false;
    }

    int levelCompare(GeneralDevelopmentCard card) {
        if (hasSameLevel(card))
            return 0;
        return getCardLevel().compareTo(card.getCardLevel());
    }
}
