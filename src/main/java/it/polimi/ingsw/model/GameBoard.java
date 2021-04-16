package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithTrack;
import it.polimi.ingsw.model.gameresources.faithtrack.Section;
import it.polimi.ingsw.model.gameresources.markettray.MarketMarble;
import it.polimi.ingsw.model.gameresources.markettray.MarketTray;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents the game board, that's a collection of elements that are common to all players.
 */
public class GameBoard {

    FaithTrack faithTrack;
    DevelopmentCardsGrid developmentCardGrid;
    MarketTray marketTray;


    /**
     * Method that initializes the elements of this game board.
     * @param listOfPlayers -> array that contains all the players.
     * @return
     */
    public GameBoard prepareGameBoard(ArrayList<Player> listOfPlayers) throws Exception {
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
                MarketMarble marble = (MarketMarble) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(MarketMarble.class, jsonPath, new int[] {i});
                howManyMarbles.put(marble, quantity);
            } catch (Exception e) {
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
            } catch (Exception e) {
                break;
            }
        }
        developmentCardGrid = new DevelopmentCardsGrid(cards, rows, columns);
    }

    /*
    private Section getSectionFromJSON(int sectionCounter){
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        return ;
    }*/


    /**
     * Getter method for "faithTrack" attribute of this class.
     * @return -> the faith track.
     */
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

}
