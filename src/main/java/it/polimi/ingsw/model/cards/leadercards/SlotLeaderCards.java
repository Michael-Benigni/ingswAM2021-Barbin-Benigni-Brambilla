package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.exception.LeaderCardNotFoundException;

import java.util.ArrayList;

public class SlotLeaderCards {
    ArrayList <LeaderCard> listOfLeaderCards = new ArrayList<>(0);
    int maxNumberOfCards;

    public SlotLeaderCards(int maxNumberOfCards) {
        this.maxNumberOfCards = maxNumberOfCards;
    }

    void addLeaderCardToSlot(LeaderCard cardToAdd){
        if(this.listOfLeaderCards.size() <= maxNumberOfCards)
            this.listOfLeaderCards.add(cardToAdd);
    }

    void discardLeaderCard(LeaderCard cardToRemove) throws LeaderCardNotFoundException {
        if( ! this.listOfLeaderCards.contains(cardToRemove) )
            throw new LeaderCardNotFoundException();
        else
            this.listOfLeaderCards.remove(cardToRemove);
    }




    //discard (LeaderCard leaderCard) {}
    // play (LeaderCard leaderCard) {}
    //private boolean containsThis (LeaderCard leaderCard) {}



}
