package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.LeaderCardNotFoundException;
import java.util.ArrayList;

/**
 * class that represents the place
 * where we can put and handle the
 * leader cards of the players
 */
public class SlotLeaderCards {
    private ArrayList <LeaderCard> listOfLeaderCards;
    private int maxNumberOfCards;

    /**
     * constructor method of this class
     * @param maxNumberOfCards it is the capacity of the slot
     */
    public SlotLeaderCards(int maxNumberOfCards) {
        this.listOfLeaderCards = new ArrayList<>(0);
        this.maxNumberOfCards = maxNumberOfCards;
    }

    /**
     * this method adds the specified
     * leader card to the slot leader card
     * @param cardToAdd it is the card we want to add
     */
    void addLeaderCardToSlot(LeaderCard cardToAdd){
        if(this.listOfLeaderCards.size() < maxNumberOfCards)
            this.listOfLeaderCards.add(cardToAdd);
    }

    /**
     * this method removes the specified
     * leader card from the slot leader card
     * @param cardToRemove it is the card we want to remove
     * @throws LeaderCardNotFoundException
     */
    void discardLeaderCard(LeaderCard cardToRemove) throws LeaderCardNotFoundException {
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
}