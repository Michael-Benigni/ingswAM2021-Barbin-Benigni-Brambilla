package it.polimi.ingsw.server.model.cards.leadercards;

import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.server.model.exception.FullLeaderCardSlotException;
import it.polimi.ingsw.server.model.exception.LeaderCardNotFoundException;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayList;


/**
 * class that represents the place
 * where we can put and handle the
 * leader cards of the players
 */
public class SlotLeaderCards implements GameComponent {
    private final ArrayList <LeaderCard> listOfLeaderCards;
    private final int maxNumberOfCards;
    private final int maxNumOfCardsDuringGame;


    /**
     * @return the max capacity of the SlotLeaderCard
     */
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
        notifyUpdate(generateUpdate(cards, new ArrayList<>(0)));
    }

    Sendable generateUpdate(ArrayList<LeaderCard> cardsNotPlayed, ArrayList<LeaderCard> cardsPlayed){
        MessageWriter writer = new MessageWriter();
        writer.setHeader (Header.ToClient.INIT_LEADER_CARDS);
        writer.addProperty ("cardsNotPlayed", buildIDsList(cardsNotPlayed));
        writer.addProperty("descriptionsNotPlayed", buildDescriptions(cardsNotPlayed));
        writer.addProperty ("cardsPlayed", buildIDsList(cardsPlayed));
        writer.addProperty("descriptionsPlayed", buildDescriptions(cardsPlayed));
        return writer.write ();
    }

    /**
     * this method creates an array list of descriptions that represent the cards passed like parameters
     * @param cards we want to build an array of the descriptions of this list of cards
     * @return the array of descriptions of the cards passed like parameters
     */
    private ArrayList<String> buildDescriptions(ArrayList<LeaderCard> cards){
        ArrayList<String> listOfDescriptions = new ArrayList<>(0);
        if(!cards.isEmpty()){
            for(int i = 0; i < cards.size(); i++){
                listOfDescriptions.add(cards.get(i).toString());
            }
        }
        return listOfDescriptions;
    }

    /**
     * this method creates an array list of IDs of the cards passed like parameters
     * @param cards we want to build an array of the IDs of this list of cards
     * @return the array of IDs of the cards passed like parameters
     */
    private ArrayList<Integer> buildIDsList(ArrayList<LeaderCard> cards){
        ArrayList<Integer> listOfIDs = new ArrayList<>(0);
        if(!cards.isEmpty()){
            for(int i = 0; i < cards.size(); i++){
                listOfIDs.add(cards.get(i).getCardID());
            }
        }
        return listOfIDs;
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
        notifyUpdate(generateUpdate(getAllNotPlayedCards(), getAllPlayedCards()));
    }


    /**
     * @param cardIndex index of the LeaderCard in this LeaderCardsSlot
     * @return the LeaderCard at the index specified
     */
    public LeaderCard get(int cardIndex)  {
        return this.listOfLeaderCards.get(cardIndex);
    }

    /**
     * @return all the inactive LeaderCards of the LeaderCardsSlot
     */
    public ArrayList<LeaderCard> getAllNotPlayedCards() {
        ArrayList<LeaderCard> allCards = new ArrayList<>();
        for (LeaderCard card : this.listOfLeaderCards) {
            if(!card.isAlreadyPlayed())
                allCards.add(card);
        }
        return allCards;
    }

    /**
     * @return all the active LeaderCards of the LeaderCardsSlot
     */
    public ArrayList<LeaderCard> getAllPlayedCards() {
        ArrayList<LeaderCard> allCards = new ArrayList<>();
        for (LeaderCard card : this.listOfLeaderCards) {
            if(card.isAlreadyPlayed())
                allCards.add(card);
        }
        return allCards;
    }


    /**
     * @return true if the initial conditions to start the game are satisfied, otherwise returns false
     */
    public boolean isReadyToStart() {
        return this.listOfLeaderCards.size() == maxNumOfCardsDuringGame;
    }

    @Override
    public Iterable<Observer> getObservers() {
        return null;
    }

    @Override
    public void attach(Observer observer) {

    }
}