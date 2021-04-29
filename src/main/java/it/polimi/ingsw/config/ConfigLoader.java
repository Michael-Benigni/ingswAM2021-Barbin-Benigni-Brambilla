package it.polimi.ingsw.config;

import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithTrack;
import it.polimi.ingsw.model.gameresources.markettray.MarketMarble;
import it.polimi.ingsw.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigLoader {
    private JsonHandler jsonHandler;

    private GameBoard loadGameBoard() throws FileNotFoundException {
        return new GameBoard(
                initFaithTrackFromJSON(),
                initCardsGridFromJSON(),
                initMarketFromJSON(),
                initLeaderCardsDeckFromJSON()
        );
    }

    public PersonalBoard loadPersonalBoard() throws FileNotFoundException {
        final String keyInJSON = "personalBoard/";
        int maxNumberOfDevCardsInSlot = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxNumOfDevCardsInSlot/");
        int numberSlotDevCards = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "numOfSlotDevCards/");
        int maxLeaderCardsInSlot = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxNumOfLeaderCardsInSlot/");
        return new PersonalBoard(initWarehouseFromJSON(), numberSlotDevCards, maxNumberOfDevCardsInSlot, maxLeaderCardsInSlot);
    }

    private FaithTrack initFaithTrackFromJSON() {
        String jsonPath = "gameBoard/marketTray/";
        return null;
    }

    private MarketTray initMarketFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/marketTray/";
        int rows = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "rows");
        int columns = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "columns");
        int numOfMarblesTypes = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numOfMarblesType");
        HashMap<MarketMarble, Integer> howManyMarbles = new HashMap<>();
        jsonPath = jsonPath + "howManyMarbles/";
        for (int i = 0; i < numOfMarblesTypes; i++) {
            int quantity = (int) JsonHandler.getAsJavaObjectFromJSONArray(int.class, jsonPath + "quantity", new int[] {i});
            MarketMarble marble = (MarketMarble) JsonHandler.getAsJavaObjectFromJSONArray(MarketMarble.class, jsonPath + "marble", new int[] {i});
            howManyMarbles.put(marble, quantity);
        }
        return new MarketTray(columns, rows, howManyMarbles);
    }

    private DevelopmentCardsGrid initCardsGridFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/developmentCardsGrid/";
        int rows = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "rows");
        int columns = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "columns");
        int numOfCards = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numOfCards");
        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++) {
            DevelopmentCard card = (DevelopmentCard) JsonHandler.getAsJavaObjectFromJSONArray(DevelopmentCard.class, jsonPath + "cardsList/", new int[] {i});
            cards.add(card);
        }
        return new DevelopmentCardsGrid(cards, rows, columns);
    }

    private LeaderCardsDeck initLeaderCardsDeckFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/leaderCardsDeck/deck/";
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        int numOfCards = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numOfCards");
        for (int i = 0; i < numOfCards; i++) {
            LeaderCard card = (LeaderCard) JsonHandler.getAsJavaObjectFromJSONArray(LeaderCard.class, jsonPath, new int[] {i});
            setEffectFromJSON(card, jsonPath, new int[] {i});
            leaderCards.add(card);
        }
        return new LeaderCardsDeck(leaderCards);
    }


    /**
     * this method is used to set the effect of the leader card from the database
     * @param jsonPath is the path of the file that represents the database
     * @param ints is an index used to pick the information from the json file
     * @throws FileNotFoundException
     */
    private void setEffectFromJSON(LeaderCard card, String jsonPath, int[] ints) throws FileNotFoundException {
        jsonPath = jsonPath + "effect/";
        String typeEffect = (String) JsonHandler.getAsJavaObjectFromJSONArray(String.class, jsonPath + "effectType", ints);
        StorableResource resource = (StorableResource) JsonHandler.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
        switch (typeEffect) {
            case "discount": {
                card.setDiscountEffect(resource);
                break;
            }
            case "extraDepot": {
                int depotCapacity = (int) JsonHandler.getAsJavaObjectFromJSONArray(int.class, jsonPath + "capacity", ints);
                card.setExtraDepotEffect(resource, depotCapacity);
                break;
            }
            case "extraProductionPower": {
                card.setExtraProductionPowerEffect(resource);
                break;
            }
            case "transformWhiteMarble": {
                card.setWhiteMarbleTransformationEffect(resource);
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * this method initializes the warehouse
     * of the player from the database
     * @throws FileNotFoundException
     */
    private WarehouseDepots initWarehouseFromJSON() throws FileNotFoundException {
        final String keyInJSON = "personalBoard/warehouseDepots/";
        int numberOfDepots = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "numOfDepots/");
        ArrayList<Integer> capacities = new ArrayList<>(numberOfDepots);
        for (int i = 0; i < numberOfDepots; i++) {
            int capacity = (int) JsonHandler.getAsJavaObjectFromJSONArray(int.class, keyInJSON + "capacities/", new int[] {i});
            capacities.add(capacity);
        }
        return new WarehouseDepots(numberOfDepots, capacities);
    }
}
