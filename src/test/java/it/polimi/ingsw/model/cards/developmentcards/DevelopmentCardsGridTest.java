package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.exception.EmptyDeckException;
import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.model.VictoryPoint;
import it.polimi.ingsw.model.gameresources.markettray.Resource;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardsGridTest {

    /**
     * method that checks if each
     * deck contains cards
     * with the same colour and level
     * @throws NegativeResourceAmountException
     * @throws EmptyDeckException
     */
    @Test
    void checkDecks() throws NegativeResourceAmountException, EmptyDeckException, NegativeVPAmountException {
        int numberOfRows = 2, numberOfColumns = 3;
        ArrayList <DevelopmentCard> cardsList = buildCardsForTests();
        int numberOfCardsInEachDeck = cardsList.size() / (numberOfRows * numberOfColumns);
        DevelopmentCardsGrid cardsGrid = new DevelopmentCardsGrid(cardsList, numberOfRows, numberOfColumns);
        for(int i = 0; i < numberOfRows; i++) {
            for(int j = 0; j < numberOfColumns; j++) {
                DevelopmentCard card = cardsGrid.getChoosenCard(i, j);
                for(int k = 1; k < numberOfCardsInEachDeck; k++) {
                    cardsGrid.removeChoosenCardFromGrid(i, j);
                    if(!card.hasSameColour(cardsGrid.getChoosenCard(i, j)) || !card.hasSameLevel(cardsGrid.getChoosenCard(i, j))) {
                        fail();
                    }
                }
            }
        }
    }

    /**
     * method that check if
     * each row contains decks
     * with the same level
     * @throws NegativeResourceAmountException
     * @throws EmptyDeckException
     */
    @Test
    void checkRows() throws NegativeResourceAmountException, EmptyDeckException, NegativeVPAmountException {
        int numberOfRows = 2, numberOfColumns = 3;
        ArrayList <DevelopmentCard> cardsList = buildCardsForTests();
        DevelopmentCardsGrid cardsGrid = new DevelopmentCardsGrid(cardsList, numberOfRows, numberOfColumns);
        DevelopmentCard card;
        for(int i = 0; i < numberOfRows; i++) {
            card = cardsGrid.getChoosenCard(i, 0);
            for (int j = 1; j < numberOfColumns; j++) {
                if(!card.hasSameLevel(cardsGrid.getChoosenCard(i, j))) {
                    fail();
                }
            }
        }
    }

    /**
     * method that check if
     * each column contains decks
     * with the same colour
     * @throws NegativeResourceAmountException
     * @throws EmptyDeckException
     */
    @Test
    void checkColumns() throws NegativeResourceAmountException, EmptyDeckException, NegativeVPAmountException {
        int numberOfRows = 2, numberOfColumns = 3;
        ArrayList <DevelopmentCard> cardsList = buildCardsForTests();
        DevelopmentCardsGrid cardsGrid = new DevelopmentCardsGrid(cardsList, numberOfRows, numberOfColumns);
        DevelopmentCard card;
        for(int i = 0; i < numberOfColumns; i++) {
            card = cardsGrid.getChoosenCard(0, i);
            for (int j = 1; j < numberOfRows; j++) {
                if(!card.hasSameColour(cardsGrid.getChoosenCard(j, i))) {
                    fail();
                }
            }
        }
    }

    /**
     * this method creates all
     * the development cards
     * we need to run the tests.
     * @return a list that contains all the created development cards
     * @throws NegativeResourceAmountException
     */
    private ArrayList <DevelopmentCard> buildCardsForTests() throws NegativeResourceAmountException, NegativeVPAmountException {
        ArrayList <DevelopmentCard> cardsList = new ArrayList<>(0);
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        ArrayList <StorableResource> cost = new ArrayList <> (0);
        ArrayList <StorableResource> consumedResources = new ArrayList <> (0);
        ArrayList <Resource> producedResources = new ArrayList <> (0);
        cost.add(servant);
        consumedResources.add(servant);
        producedResources.add(servant);
        VictoryPoint victoryPoints = new VictoryPoint(4);
        DevelopmentCard card1 = new DevelopmentCard(CardColour.YELLOW, CardLevel.TWO, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card2 = new DevelopmentCard(CardColour.YELLOW, CardLevel.TWO, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card3 = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card4 = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card5 = new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card6 = new DevelopmentCard(CardColour.BLUE, CardLevel.TWO, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card7 = new DevelopmentCard(CardColour.BLUE, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card8 = new DevelopmentCard(CardColour.BLUE, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card9 = new DevelopmentCard(CardColour.GREEN, CardLevel.TWO, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card10 = new DevelopmentCard(CardColour.GREEN, CardLevel.TWO, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card11 = new DevelopmentCard(CardColour.GREEN, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        DevelopmentCard card12 = new DevelopmentCard(CardColour.GREEN, CardLevel.ONE, cost, consumedResources, producedResources, victoryPoints);
        cardsList.add(card11);
        cardsList.add(card9);
        cardsList.add(card12);
        cardsList.add(card10);
        cardsList.add(card4);
        cardsList.add(card6);
        cardsList.add(card8);
        cardsList.add(card2);
        cardsList.add(card3);
        cardsList.add(card5);
        cardsList.add(card1);
        cardsList.add(card7);
        Collections.shuffle(cardsList);
        return cardsList;
    }
}