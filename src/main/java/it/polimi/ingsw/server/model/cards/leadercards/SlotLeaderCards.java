package it.polimi.ingsw.server.model.cards.leadercards;

import it.polimi.ingsw.server.exception.FullLeaderCardSlotException;
import it.polimi.ingsw.server.exception.LeaderCardNotFoundException;
import java.util.ArrayList;

/**
 * class that represents the place
 * where we can put and handle the
 * leader cards of the players
 */
public class SlotLeaderCards {
    private ArrayList <LeaderCard> listOfLeaderCards;
    private final int maxNumberOfCards;
    private final int maxNumOfCardsDuringGame;


    public int getMaxNumberOfCards() {
        return maxNumberOfCards;
    }

    /**
     * constructor method of this class
     * @param maxNumberOfCards it is the capacity of the slot
     */
    public SlotLeaderCards(int maxNumberOfCards, int maxNumOfCardsDuringGame) {
        this.listOfLeaderCards = new ArrayList<>(0);
        this.maxNumberOfCards = maxNumberOfCards;
        this.maxNumOfCardsDuringGame = maxNumOfCardsDuringGame;
    }

    /**
     * This method initializes the slot with the cards input. If the cards are more than the max capacity, then from the
     * (cards.size() - maxNumberOfCards)^th card, the following are all discarded, not considered
     * @param cards
     */
    public void init(ArrayList<LeaderCard> cards) {
        for (LeaderCard card : cards) {
            try {
                this.add(card);
            } catch (FullLeaderCardSlotException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this method adds the specified
     * leader card to the slot leader card
     * @param cardToAdd it is the card we want to add
     */
    private void add(LeaderCard cardToAdd) throws FullLeaderCardSlotException {
        if(this.listOfLeaderCards.size() <= maxNumberOfCards)
            this.listOfLeaderCards.add(cardToAdd);
        else
            throw new FullLeaderCardSlotException();
    }

    /**
     * this method removes the specified
     * leader card from the slot leader card
     * @param cardToRemove it is the card we want to remove
     * @throws LeaderCardNotFoundException
     */
    public void remove(LeaderCard cardToRemove) throws LeaderCardNotFoundException {
        if( ! this.listOfLeaderCards.contains(cardToRemove) )
            throw new LeaderCardNotFoundException();
        else
            this.listOfLeaderCards.remove(cardToRemove);
    }

    public LeaderCard get(int cardIndex) throws LeaderCardNotFoundException {
        if(!listOfLeaderCards.contains(cardIndex))
            throw new LeaderCardNotFoundException();
        return this.listOfLeaderCards.get(cardIndex);
    }

    public ArrayList<LeaderCard> getAllActiveCards() {
        ArrayList<LeaderCard> allCards = new ArrayList<>();
        for (LeaderCard card : this.listOfLeaderCards) {
            if(card.isAlreadyPlayed())
                allCards.add(card);
        }
        return allCards;
    }

    public boolean isReadyToStart() {
        return this.listOfLeaderCards.size() == maxNumOfCardsDuringGame;
    }
}