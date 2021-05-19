package it.polimi.ingsw.server.model.cards.leadercards;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Subject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * class that represents the initial deck of the leader card before the distribution of the cards to the players
 */
public class LeaderCardsDeck  {
    private final ArrayList <LeaderCard> deck;


    /**
     * constructor method of this class
     * @param deck
     */
    public LeaderCardsDeck(ArrayList<LeaderCard> deck) {
        this.deck = deck;
        shuffleDeck();
    }


    /**
     * method that selects and removes a leader card from the deck
     * @param index is the position of the selected card in the deck
     * @return the choosen card
     */
    private LeaderCard getLeaderCard(int index) {
        LeaderCard card = this.deck.get(index);
        this.deck.remove(index);
        return card;
    }


    /**
     * method that shuffles the leader cards deck
     */
    void shuffleDeck() {
        Collections.shuffle(this.deck);
    }


    /**
     *
     * @param howManyCards
     * @return
     */
    public ArrayList<LeaderCard> draw(int howManyCards) {
        ArrayList<LeaderCard> cards = new ArrayList();
        for (int i = 0; i < howManyCards; i++) {
            cards.add(getLeaderCard(0));
        }
        return cards;
    }
}
