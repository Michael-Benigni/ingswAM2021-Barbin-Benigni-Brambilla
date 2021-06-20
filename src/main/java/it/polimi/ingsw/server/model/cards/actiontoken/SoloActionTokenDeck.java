package it.polimi.ingsw.server.model.cards.actiontoken;

import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.utils.Observer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that represents the deck of action tokens used in the singleplayer game.
 */
public class SoloActionTokenDeck implements GameComponent {

    private final ArrayList<SoloActionToken> listOfTokens;
    private ArrayList<Observer> observers;

    /**
     * Constructor method of this class.
     * @param listOfTokens
     */
    public SoloActionTokenDeck(ArrayList<SoloActionToken> listOfTokens) {
        this.observers = new ArrayList<> ();
        this.listOfTokens = listOfTokens;
    }

    /**
     * Method that change the order of the action tokens.
     */
    void shuffle() {
        Collections.shuffle(this.listOfTokens);
    }

    /**
     * Method that swaps the deck, then returns the last token.
     * @return
     */
    public SoloActionToken drawFirst() {
        swap();
        return this.listOfTokens.get(listOfTokens.size() - 1);
    }

    /**
     * Method that swap the entire array of tokens and put the first element to the bottom.
     */
    private void swap() {
        SoloActionToken tempToken = listOfTokens.get(0);
        for(int i = 0; i < listOfTokens.size(); i++) {
            if(i == listOfTokens.size() - 1) {
                listOfTokens.remove(i);
                listOfTokens.add(i, tempToken);
            }
            else {
                listOfTokens.remove(i);
                listOfTokens.add(i, listOfTokens.get(i));
            }
        }
    }

    @Override
    public ArrayList<Observer> getObservers() {
        return this.observers;
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

    @Override
    public void detach(Observer observer) {
        getObservers ().remove(observer);
    }
}
