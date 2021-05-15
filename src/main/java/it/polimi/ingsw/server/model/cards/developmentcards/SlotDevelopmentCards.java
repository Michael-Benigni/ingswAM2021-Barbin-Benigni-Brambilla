package it.polimi.ingsw.server.model.cards.developmentcards;

import it.polimi.ingsw.server.model.exception.DevelopmentCardNotAddableException;
import it.polimi.ingsw.server.model.exception.EmptySlotException;
import it.polimi.ingsw.server.model.exception.SlotDevelopmentCardsIsFullException;
import it.polimi.ingsw.server.model.gamelogic.actions.Producer;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.Publisher;

import java.util.ArrayList;
import java.util.Objects;

/**
 * this class models a single slot where the player can place his development cards
 */
public class SlotDevelopmentCards extends Producer implements Publisher {
    private Integer maxNumberOfCardsInSlot;
    private ArrayList <DevelopmentCard> listOfDevelopmentCards = new ArrayList<>(0);
    private final ArrayList <Observer> observers;
    private Observer owner;


    /**
     * constructor of the class SlotDevelopmentCards
     */
    public SlotDevelopmentCards(int maxNumberOfCardsInSlot) {
        this.maxNumberOfCardsInSlot = maxNumberOfCardsInSlot;
        this.observers = new ArrayList<>();
    }

    /**
     * this method provides the development card that is on top of the slot to the caller
     * @return the created copy of the top development card
     */
    public DevelopmentCard getTopCard() throws EmptySlotException {
        if(!listOfDevelopmentCards.isEmpty()) {
            DevelopmentCard topCard = (DevelopmentCard) listOfDevelopmentCards.get((listOfDevelopmentCards.size() - 1)).clone();
            return topCard; //the first position in the list is occupied by the last added development card
        }
        throw new EmptySlotException();
    }

    /**
     * this method provides all the cards that are placed in the slot
     * the array is filled starting from the card on top of the slot
     * @return an array list of DevelopmentCard
     */
    public ArrayList <DevelopmentCard> getAllCards() throws EmptySlotException {
        ArrayList <DevelopmentCard> listOfAllCards = new ArrayList<>(0);
        ArrayList <DevelopmentCard> auxiliaryList = new ArrayList<>(0);
        auxiliaryList.add(this.getTopCard());
        for(int i = listOfDevelopmentCards.size() - 2; i >= 0; i--) {
            auxiliaryList.add((DevelopmentCard) listOfDevelopmentCards.get(i).clone());
        }
        for(int j = auxiliaryList.size() - 1; j >= 0; j-- ) {
            listOfAllCards.add(auxiliaryList.get(j));
        }
        return listOfAllCards;
    }

    /**
     * this method adds a new
     * development card on top of the slot
     * @param cardToAdd -> the card that the player wants to add
     * @throws DevelopmentCardNotAddableException
     * @throws SlotDevelopmentCardsIsFullException
     */
    public void placeOnTop (DevelopmentCard cardToAdd) throws Exception {
        if (listOfDevelopmentCards.size() < maxNumberOfCardsInSlot) {
            DevelopmentCard card;
            try {
                card = getTopCard();
            } catch (EmptySlotException exc) {
                card = null;
            }
            if (cardToAdd.isOfNextLevel(card)) {
                this.listOfDevelopmentCards.add(cardToAdd);
                return;
            }
            throw new DevelopmentCardNotAddableException();
        }
        throw new SlotDevelopmentCardsIsFullException();
    }

    /**
     * equals method.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotDevelopmentCards that = (SlotDevelopmentCards) o;
        return Objects.equals(maxNumberOfCardsInSlot, that.maxNumberOfCardsInSlot)
                && Objects.equals(listOfDevelopmentCards, that.listOfDevelopmentCards);
    }

    /**
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(maxNumberOfCardsInSlot, listOfDevelopmentCards);
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

    }
}