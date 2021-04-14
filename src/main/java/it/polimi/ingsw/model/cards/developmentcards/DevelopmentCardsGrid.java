package it.polimi.ingsw.model.cards.developmentcards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import it.polimi.ingsw.exception.EmptyDeckException;

/**
 * this class models the grid that contains all
 * the development cards at the start of the game
 * here the players can buy their development cards
 * this class has been implemented by a three-dimensional ArrayList of DevelopmentCard
 * the cards shown on the top of the decks are placed in the arrays in the last position
 */
public class DevelopmentCardsGrid {
    private Integer numberOfColumns;
    private Integer numberOfRows;
    private ArrayList <ArrayList <ArrayList <DevelopmentCard>>> cardsGrid;

    /**
     * this is the constructor method for the class DevelopmentCardsGrid
     * this constructor invokes the method setCardsGrid to build
     * the cards grid with the cards divided by colour and level
     */
    public DevelopmentCardsGrid(ArrayList<DevelopmentCard> cardsList, int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        initGrid();
        setCardsGrid(cardsList);
    }

    /**
     * method that shuffles the
     * cards in each deck that
     * composes the grid
     */
    private void shuffleDecks() {
        for (int i = 0; i < numberOfRows; i++){
            for (int j = 0; j < numberOfColumns; j++) {
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
    DevelopmentCard getChoosenCard(int iPos, int jPos) throws EmptyDeckException {
        DevelopmentCard choosenCard;
        ArrayList <DevelopmentCard> choosenDeck = getDeck(iPos, jPos);
        int choosenDeckLastIndex = choosenDeck.size() - 1;
        choosenCard = (DevelopmentCard) choosenDeck.get(choosenDeckLastIndex).clone();
        return choosenCard;
    }

    /**
     * this method removes the card on top of one of the deck in the grid
     * this method can be called after the player has choosen the card
     * @param iPos -> index of the row
     * @param jPos -> index of the column
     * @throws EmptyDeckException -> thrown by getDeck if the deck is empty
     */
    void removeChoosenCardFromGrid (int iPos, int jPos) throws EmptyDeckException {
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
     * @param cardsList -> the list of all the development cards, picked from the database
     */
    private void setCardsGrid(ArrayList <DevelopmentCard> cardsList) {
        ArrayList <DevelopmentCard> cardsGroupedByColour = groupByColour(cardsList);
        ArrayList <DevelopmentCard> cardsGroupedByColourSortedByLevel = sortByLevel(cardsGroupedByColour);
        int numberOfDecks = numberOfRows * numberOfColumns;
        int numberOfCardsInEachDeck = cardsGroupedByColourSortedByLevel.size() / numberOfDecks;
        for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                List<DevelopmentCard> deck = cardsGroupedByColourSortedByLevel.subList(0, numberOfCardsInEachDeck);
                this.cardsGrid.get(i).add(new ArrayList<>(deck));
                cardsGroupedByColourSortedByLevel.removeAll(new ArrayList<>(deck));
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
        for (int i = 0; i < numberOfColumns; i++) {
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
        cardsGroupedByColour.sort( (card1, card2) -> {
            if(card1.isOfNextLevel(card2))
                return -1;
            return 1;
        });
        return cardsGroupedByColour;
    }

    /**
     * this method initializes the
     * attribute cardsGrid
     */
    private void initGrid() {
        this.cardsGrid = new ArrayList<>(0);
        for(int i = 0; i < numberOfRows; i++){
            cardsGrid.add(new ArrayList<>());
        }
    }
}