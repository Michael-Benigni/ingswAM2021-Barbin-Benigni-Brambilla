package it.polimi.ingsw.utils.config;

import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionToken;
import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionTokenDeck;
import it.polimi.ingsw.server.model.cards.developmentcards.GeneralDevelopmentCard;
import it.polimi.ingsw.server.model.exception.EmptyDeckException;
import it.polimi.ingsw.server.model.gamelogic.actions.SoloPlayerGameBoard;
import it.polimi.ingsw.server.model.gameresources.faithtrack.SoloPlayerFaithTrack;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.server.model.gamelogic.InitialParams;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithTrack;
import it.polimi.ingsw.server.model.gameresources.faithtrack.Section;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketMarble;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ConfigLoader {
    private JsonHandler jsonHandler;

    public ConfigLoader() {
        this.jsonHandler = new JsonHandler(Prefs.getDBPath());
    }

    public GameBoard loadGameBoard() throws FileNotFoundException, EmptyDeckException {
        return new GameBoard(
                initFaithTrackFromJSON(),
                initCardsGridFromJSON(),
                initMarketFromJSON(),
                initLeaderCardsDeckFromJSON()
        );
    }

    public SoloPlayerGameBoard loadGameBoard1P() throws FileNotFoundException, EmptyDeckException {
        return new SoloPlayerGameBoard(
                initFaithTrack1PFromJSON(),
                initCardsGridFromJSON(),
                initMarketFromJSON(),
                initLeaderCardsDeckFromJSON(),
                initActionTokenDeckFromJSON()
        );
    }

    private SoloPlayerFaithTrack initFaithTrack1PFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/faithTrack/";
        int numOfSections = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numberOfSections/");
        return new SoloPlayerFaithTrack (getSectionsFromJSON (jsonPath, numOfSections));
    }

    private SoloActionTokenDeck initActionTokenDeckFromJSON() throws FileNotFoundException {
        final String keyInJSON = "gameBoard/actionTokensDeck/";
        int numActionTokens = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "numOfActionTokens/");
        ArrayList<SoloActionToken> tokens = new ArrayList<> ();
        for (int i = 0; i < numActionTokens; i++) {
            SoloActionToken newToken = new SoloActionToken ();
            setTokenEffectFromJSON(newToken, keyInJSON + "tokens/", i);
            tokens.add (newToken);
        }
        return new SoloActionTokenDeck (tokens);
    }

    private void setTokenEffectFromJSON(SoloActionToken newToken, String keyInJSON, int index) throws FileNotFoundException {
        int[] ints = {index};
        String typeEffect = (String) jsonHandler.getAsJavaObjectFromJSONArray(String.class, keyInJSON + "effect/", ints);
        switch (typeEffect) {
            case "Discard2Cards": {
                GeneralDevelopmentCard card = (GeneralDevelopmentCard) jsonHandler.getAsJavaObjectFromJSONArray (GeneralDevelopmentCard.class, keyInJSON + "card/", ints);
                int numOfCards = (int) jsonHandler.getAsJavaObjectFromJSONArray (int.class, keyInJSON + "numCardToDiscard/", ints);
                newToken.setDiscardNCardsEffect (card, numOfCards);
                newToken.setDescription (numOfCards + " Development Cards of colour " + card.getCardColour() +  " have been discarded");
                break;
            }
            case "MoveBlackCross&Shuffle": {
                int numMoves = (int) jsonHandler.getAsJavaObjectFromJSONArray (int.class, keyInJSON + "numMoves/", ints);
                newToken.setMoveBlackCrossAndReShuffle (numMoves);
                newToken.setDescription ("The BLACK CROSS has been moved by " + numMoves +  " steps. The Action-Token Deck has been shuffled");
                break;
            }
            case "MoveBlackCross": {
                int numMoves = (int) jsonHandler.getAsJavaObjectFromJSONArray (int.class, keyInJSON + "numMoves/", ints);
                newToken.setMoveBlackCross (numMoves);
                newToken.setDescription ("The BLACK CROSS has been moved by " + numMoves +  " steps.");
                break;
            }
            default: {
                break;
            }
        }
    }

    public ArrayList<PersonalBoard> loadPersonalBoards(int numOfPlayers) throws FileNotFoundException {
        final String keyInJSON = "personalBoard/";
        int maxNumberOfDevCardsInSlot = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxNumOfDevCardsInSlot/");
        int numberSlotDevCards = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "numOfSlotDevCards/");
        int maxLeaderCardsInSlot = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxNumOfLeaderCardsInSlot/");
        int maxLeaderCardsDuringGame = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxLeaderCardsDuringGame/");
        int numOfResourcesForProduction = (int) jsonHandler.getAsJavaObjectFromJSON (int.class, keyInJSON + "numOfResourcesForProduction/");
        ArrayList<PersonalBoard> boards = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++)
            boards.add(new PersonalBoard(numOfResourcesForProduction, initWarehouseFromJSON(), numberSlotDevCards, maxNumberOfDevCardsInSlot, maxLeaderCardsInSlot, maxLeaderCardsDuringGame));
        return boards;
    }

    public ArrayList<InitialParams> loadInitialParams(int maxNumOfPlayers) throws FileNotFoundException {
        final String keyInJSON = "initialParams/";
        ArrayList<InitialParams> paramsArrayList = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<> (Arrays.asList ("first/", "second/", "third/", "fourth/"));
        for (int i = 0; i < maxNumOfPlayers; i++) {
            InitialParams params = (InitialParams) jsonHandler.getAsJavaObjectFromJSON(InitialParams.class, keyInJSON + keys.get(i));
            paramsArrayList.add(params);
        }
        return paramsArrayList;
    }

    private FaithTrack initFaithTrackFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/faithTrack/";
        int numOfSections = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numberOfSections/");
        ArrayList<Section> sections = getSectionsFromJSON(jsonPath, numOfSections);
        return new FaithTrack(sections);
    }

    private ArrayList<Section> getSectionsFromJSON(String jsonPath, int numOfSections) throws FileNotFoundException {
        ArrayList<Section> sections = new ArrayList<>();
        for (int i = 0; i < numOfSections; i++) {
            Section section = (Section) jsonHandler.getAsJavaObjectFromJSONArray(Section.class, jsonPath + "listOfSections/", new int[] {i});
            sections.add(section);
        }
        return sections;
    }

    private MarketTray initMarketFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/marketTray/";
        int rows = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "rows");
        int columns = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "columns");
        int numOfMarblesTypes = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numOfMarblesType");
        HashMap<MarketMarble, Integer> howManyMarbles = new HashMap<>();
        jsonPath = jsonPath + "howManyMarbles/";
        for (int i = 0; i < numOfMarblesTypes; i++) {
            int quantity = (int) jsonHandler.getAsJavaObjectFromJSONArray(int.class, jsonPath + "quantity", new int[] {i});
            MarketMarble marble = (MarketMarble) jsonHandler.getAsJavaObjectFromJSONArray(MarketMarble.class, jsonPath + "marble", new int[] {i});
            howManyMarbles.put(marble, quantity);
        }
        return new MarketTray(columns, rows, howManyMarbles);
    }

    private DevelopmentCardsGrid initCardsGridFromJSON() throws FileNotFoundException, EmptyDeckException {
        String jsonPath = "gameBoard/developmentCardsGrid/";
        int rows = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "rows");
        int columns = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "columns");
        int numOfCards = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numOfCards");
        ArrayList<DevelopmentCard> cards = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++) {
            DevelopmentCard card = (DevelopmentCard) jsonHandler.getAsJavaObjectFromJSONArray(DevelopmentCard.class, jsonPath + "cardsList/", new int[] {i});
            cards.add(card);
        }
        return new DevelopmentCardsGrid(cards, rows, columns);
    }

    private LeaderCardsDeck initLeaderCardsDeckFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/leaderCardsDeck/";
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        int numOfCards = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numOfCards");
        for (int i = 0; i < numOfCards; i++) {
            LeaderCard card = (LeaderCard) jsonHandler.getAsJavaObjectFromJSONArray(LeaderCard.class, jsonPath + "deck/", new int[] {i});
            setEffectLeaderCardFromJSON (card, jsonPath + "deck/", new int[] {i});
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
    private void setEffectLeaderCardFromJSON(LeaderCard card, String jsonPath, int[] ints) throws FileNotFoundException {
        jsonPath = jsonPath + "effect/";
        String typeEffect = (String) jsonHandler.getAsJavaObjectFromJSONArray(String.class, jsonPath + "effectType", ints);
        StorableResource resource = (StorableResource) jsonHandler.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
        switch (typeEffect) {
            case "discount": {
                card.setDiscountEffect(resource);
                break;
            }
            case "extraDepot": {
                int depotCapacity = (int) jsonHandler.getAsJavaObjectFromJSONArray(int.class, jsonPath + "capacity", ints);
                ResourceType resourceType = (ResourceType) jsonHandler.getAsJavaObjectFromJSONArray(ResourceType.class, jsonPath + "resourceType", ints);
                card.setExtraDepotEffect(resourceType, depotCapacity);
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
        int numberOfDepots = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "numOfDepots/");
        ArrayList<Integer> capacities = new ArrayList<>(numberOfDepots);
        for (int i = 0; i < numberOfDepots; i++) {
            int capacity = (int) jsonHandler.getAsJavaObjectFromJSONArray(int.class, keyInJSON + "capacities/", new int[] {i});
            capacities.add(capacity);
        }
        return new WarehouseDepots(numberOfDepots, capacities);
    }
}
