package it.polimi.ingsw.server.model.cards.leadercards;

import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Publisher;

import java.util.ArrayList;
import java.util.Collections;

/**
 * class that represents the initial deck of the leader card before the distribution of the cards to the players
 */
public class LeaderCardsDeck implements Publisher {
    private ArrayList <LeaderCard> deck;
    private ArrayList <Observer> observers;


    /**
     * constructor method of this class
     * @param deck
     */
    public LeaderCardsDeck(ArrayList<LeaderCard> deck) {
        this.deck = deck;
        this.observers = new ArrayList<> ();
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

    /**
     * This method notifies a change in the status of the publisher to the Observers registered, usually
     */
    @Override
    public void publish() {

    }

    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        this.observers.add (observer);
    }
}
