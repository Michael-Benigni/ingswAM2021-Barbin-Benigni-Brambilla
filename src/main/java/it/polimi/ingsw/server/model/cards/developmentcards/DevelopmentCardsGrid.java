package it.polimi.ingsw.server.model.cards.developmentcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import it.polimi.ingsw.server.exception.EmptyDeckException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

/**
 * this class models the grid that contains all
 * the development cards at the start of the game
 * here the players can buy their development cards
 * this class has been implemented by a three-dimensional ArrayList of DevelopmentCard
 * the cards shown on the top of the decks are placed in the arrays in the last position
 */
public class DevelopmentCardsGrid {
    /**
     * this class represents the players
     * that are able to obtain a discount
     * buying development cards from the cards grid
     */
    private class PlayerWithDiscount {
        private Player playerWithDiscount;
        private StorableResource discount;

        /**
         * this is the constructor method of this class
         * @param playerWithDiscount it refers to the player that has obtained the discount
         * @param discount it indicates the type and the amount of the discount
         */
        public PlayerWithDiscount(Player playerWithDiscount, StorableResource discount) {
            this.playerWithDiscount = playerWithDiscount;
            this.discount = discount;
        }
    }

    private Integer columns;
    private Integer rows;
    private ArrayList <ArrayList <ArrayList <DevelopmentCard>>> cardsGrid;
    private ArrayList <PlayerWithDiscount> playerWithDiscounts = new ArrayList<>(0);

    /**
     * this is the constructor method for the class DevelopmentCardsGrid
     * this constructor invokes the method setCardsGrid to build
     * the cards grid with the cards divided by colour and level
     */
    public DevelopmentCardsGrid(ArrayList<DevelopmentCard> cardsList, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        initGrid();
        setCardsGrid(cardsList);
    }

    /**
     * this method adds a new player with discount in the development cards grid
     * @param player it refers to the player
     * @param resource it is the discount
     */
    public void addPlayerWithDiscount(Player player, StorableResource resource) {
        PlayerWithDiscount newPlayerWithDiscount = new PlayerWithDiscount(player, resource);
        this.playerWithDiscounts.add(newPlayerWithDiscount);
    }

    /**
     * method that shuffles the
     * cards in each deck that
     * composes the grid
     */
    private void shuffleDecks() {
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++) {
                Collections.shuffle(this.cardsGrid.get(i).get(j));
            }
        }
    }

    /**
     * this method provides the card choosen by the player to the caller
     * @param iPos index of the row
     * @param jPos index of the column
     * @return the card that the player has choosen
     * @throws EmptyDeckException -> thrown if the choosen deck is empty
     */
    public DevelopmentCard getChoosenCard(int iPos, int jPos, Player player) throws EmptyDeckException {
        DevelopmentCard choosenCard;
        ArrayList <DevelopmentCard> choosenDeck = getDeck(iPos, jPos);
        int choosenDeckLastIndex = choosenDeck.size() - 1;
        choosenCard = (DevelopmentCard) choosenDeck.get(choosenDeckLastIndex).clone();
        for(int i = 0; i < this.playerWithDiscounts.size(); i++){
            if(this.playerWithDiscounts.get(i).playerWithDiscount == player) {
                choosenCard.reduceCost(this.playerWithDiscounts.get(i).discount);
            }
        }
        return choosenCard;
    }

    /**
     * this method removes the card on top of one of the deck in the grid
     * this method can be called after the player has choosen the card
     * @param iPos -> index of the row
     * @param jPos -> index of the column
     * @throws EmptyDeckException -> thrown by getDeck if the deck is empty
     */
    public void removeChoosenCardFromGrid (int iPos, int jPos) throws EmptyDeckException {
        ArrayList <DevelopmentCard> choosenDeck = getDeck(iPos, jPos);
        int choosenDeckLastIndex = choosenDeck.size() - 1;
        choosenDeck.remove(choosenDeckLastIndex);
    }

    /**
     * this method provides the deck that is placed in the iPos row and in the jPos column
     * @param iPos -> index of the row
     * @param jPos -> index of the column
     * @return the array that represents the deck
     * @throws EmptyDeckException -> EmptyDeckException is thrown if the deck is empty
     */
    private ArrayList <DevelopmentCard> getDeck (int iPos, int jPos) throws EmptyDeckException {
        if (! this.cardsGrid.get(iPos).get(jPos).isEmpty()) {
            return this.cardsGrid.get(iPos).get(jPos);
        }
        else {
            throw new EmptyDeckException();
        }
    }

    /**
     * this method picks all the development cards in an ArrayList
     * it groups the ArrayList by colour
     * than it sorts the ArrayList by level
     * and it puts the cards in the cards grid
     * this method builds the cards grid ordered by colour and level
     * it also calls the method shuffleDecks to shuffle the cards in each deck
     * @param cardsList it is the list of all the development cards, picked from the database
     */
    private void setCardsGrid(ArrayList <DevelopmentCard> cardsList) {
        ArrayList <DevelopmentCard> cardsGroupedByColour = groupByColour(cardsList);
        ArrayList <DevelopmentCard> cardsGroupedByColourSortedByLevel = sortByLevel(cardsGroupedByColour);
        int numberOfDecks = rows * columns;
        int numberOfCardsInEachDeck = cardsGroupedByColourSortedByLevel.size() / numberOfDecks;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                List<DevelopmentCard> deck = cardsGroupedByColourSortedByLevel.subList(0, numberOfCardsInEachDeck);
                this.cardsGrid.get(i).add(new ArrayList<>(deck));
                for(int k = 0; k < numberOfCardsInEachDeck; k++){
                    cardsGroupedByColourSortedByLevel.remove(0);
                }
            }
        }
        shuffleDecks();
    }

    /**
     * method that groups a list of
     * development cards by colour
     * @param cardsList -> the list of cards we want to group
     * @return an ArrayList of DevelopmentCard grouped by colour
     */
    private ArrayList <DevelopmentCard> groupByColour(ArrayList <DevelopmentCard> cardsList) {
        ArrayList <DevelopmentCard> cardsGroupedByColour = new ArrayList<>(0);
        for (int i = 0; i < columns; i++) {
            ArrayList <DevelopmentCard> auxiliaryList = (ArrayList<DevelopmentCard>) cardsList
                    .stream()
                    .filter( card1 -> card1.hasSameColour(cardsList.get(0)))
                    .collect(Collectors.toList());
            cardsList.removeIf( card1 -> card1.hasSameColour(cardsList.get(0)));
            cardsGroupedByColour.addAll(auxiliaryList);
        }
        return cardsGroupedByColour;
    }

    /**
     * method that sorts a list
     * of development cards by level
     * @param cardsGroupedByColour -> the list of cards we want to sort
     * @return an ArrayList of DevelopmentCard sorted by level
     */
    private ArrayList <DevelopmentCard> sortByLevel(ArrayList <DevelopmentCard> cardsGroupedByColour) {
        cardsGroupedByColour.sort(GeneralDevelopmentCard::levelCompare);
        return cardsGroupedByColour;
    }

    /**
     * this method initializes the
     * attribute cardsGrid
     */
    private void initGrid() {
        this.cardsGrid = new ArrayList<>(0);
        for(int i = 0; i < rows; i++){
            cardsGrid.add(new ArrayList<>());
        }
    }
}