package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithTrack;
import it.polimi.ingsw.model.gameresources.markettray.MarketMarble;
import it.polimi.ingsw.model.gameresources.markettray.MarketTray;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents the game board, that's a collection of elements that are common to all players.
 */
public class GameBoard {

    private FaithTrack faithTrack;
    private DevelopmentCardsGrid developmentCardGrid;
    private MarketTray marketTray;
    private LeaderCardsDeck leaderCardsDeck;

    GameBoard () {

    }
    /**
     * Method that initializes the elements of this game board.
     * @param listOfPlayers -> array that contains all the players.
     * @return
     */
    GameBoard prepare(ArrayList<Player> listOfPlayers) throws FileNotFoundException {
        initLeaderCardsDeckFromJSON("gameBoard/");
        initMarketFromJSON("gameBoard/");
        initCardsGridFromJSON("gameBoard/");
        initFaithTrackFromJSON("gameBoard/");
        return this;
    }

    private void initFaithTrackFromJSON(String jsonPath) {
    }

    private void initMarketFromJSON(String jsonPath) throws FileNotFoundException {
        jsonPath = jsonPath + "marketTray/";
        int rows = (int) ConfigLoaderWriter.getAsJavaObjectFromJSON(int.class, jsonPath + "rows");
        int columns = (int) ConfigLoaderWriter.getAsJavaObjectFromJSON(int.class, jsonPath + "columns");
        HashMap<MarketMarble, Integer> howManyMarbles = new HashMap<>();
        jsonPath = jsonPath + "howManyMarbles/";
        for (int i = 0; true; i++) {
            try {
                int quantity = (int) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(int.class, jsonPath + "quantity", new int[] {i});
                MarketMarble marble = (MarketMarble) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(MarketMarble.class, jsonPath + "marble", new int[] {i});
                howManyMarbles.put(marble, quantity);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        marketTray = new MarketTray(columns, rows, howManyMarbles);
    }

    private void initCardsGridFromJSON(String jsonPath) throws FileNotFoundException {
        jsonPath = jsonPath + "developmentCardsGrid/";
        int rows = (int) ConfigLoaderWriter.getAsJavaObjectFromJSON(int.class, jsonPath + "rows");
        int columns = (int) ConfigLoaderWriter.getAsJavaObjectFromJSON(int.class, jsonPath + "columns");
        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        for (int i = 0; true; i++) {
            try {
                DevelopmentCard card = (DevelopmentCard) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(DevelopmentCard.class, jsonPath + "cardsList/", new int[] {i});
                cards.add(card);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        developmentCardGrid = new DevelopmentCardsGrid(cards, rows, columns);
    }

    private void initLeaderCardsDeckFromJSON(String jsonPath) throws FileNotFoundException {
        jsonPath = jsonPath + "leaderCardsDeck/deck/";
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        for (int i = 0; true; i++) {
            try {
                LeaderCard card = (LeaderCard) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(LeaderCard.class, jsonPath, new int[] {i});
                card.setEffectFromJSON(jsonPath, new int[] {i});
                leaderCards.add(card);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        this.leaderCardsDeck = new LeaderCardsDeck(leaderCards);
    }

    public MarketTray getMarketTray() {
        return this.marketTray;
    }

    /**
     * Getter method for "faithTrack" attribute of this class.
     * @return -> the faith track.
     */
    public FaithTrack getFaithTrack() {
        return this.faithTrack;
    }

    public DevelopmentCardsGrid getDevelopmentCardGrid() {
        return this.developmentCardGrid;
    }

}
