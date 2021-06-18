package it.polimi.ingsw.server.model.exception;


import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;

/**
 * this class manages the error caused by the attempt of adding a development card
 * with a wrong level to the slot of the personal board
 */
public class DevelopmentCardNotAddableException extends Exception {
    public DevelopmentCardNotAddableException(Integer idPlaced, Integer idToBePlaced) {
        super("Impossible to add the card ID='" + idToBePlaced.toString() + "' over the card ID='" + idPlaced.toString() + "'.");
    }

    public DevelopmentCardNotAddableException(Integer idToBePlaced) {
        super("Impossible to add the card ID='" + idToBePlaced.toString() + "' in an empty slot.");
    }
}
