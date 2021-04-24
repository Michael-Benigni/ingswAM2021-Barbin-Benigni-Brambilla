package it.polimi.ingsw.model.cards.leadercards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * class that represents the initial deck of the leader card before the distribution of the cards to the players
 */
public class LeaderCardsDeck {
    private ArrayList <LeaderCard> deck;

    /**
     * constructor method of this class
     * @param deck
     */
    public LeaderCardsDeck(ArrayList<LeaderCard> deck) {
        this.deck = deck;
    }

    /**
     * method that selects and removes a leader card from the deck
     * @param index is the position of the selected card in the deck
     * @return the choosen card
     */
    LeaderCard getLeaderCard(int index) {
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
}
