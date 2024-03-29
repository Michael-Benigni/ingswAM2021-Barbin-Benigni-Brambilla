package it.polimi.ingsw.utils.config;

import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionToken;
import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionTokenDeck;
import it.polimi.ingsw.server.model.cards.developmentcards.GeneralDevelopmentCard;
import it.polimi.ingsw.server.model.exception.EmptyDeckException;
import it.polimi.ingsw.server.model.gamelogic.actions.SoloPlayerGameBoard;
import it.polimi.ingsw.server.model.gameresources.Producible;
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

/**
 * Class that contains methods to read from a json file and create a java object.
 */
public class ConfigLoader {
    private JsonHandler jsonHandler;

    /**
     * Constructor method of this class.
     */
    public ConfigLoader() {
        this.jsonHandler = new JsonHandler(ServerPrefs.getDBPath());
    }

    /**
     * Method that creates a new game board for a multiplayer game with the information read by the database.
     */
    public GameBoard loadGameBoard() throws FileNotFoundException, EmptyDeckException {
        return new GameBoard(
                initFaithTrackFromJSON(),
                initCardsGridFromJSON(),
                initMarketFromJSON(),
                initLeaderCardsDeckFromJSON()
        );
    }

    /**
     * Method that creates a new game board for a single player game with the information read by the database.
     */
    public SoloPlayerGameBoard loadGameBoard1P() throws FileNotFoundException, EmptyDeckException {
        return new SoloPlayerGameBoard(
                initFaithTrack1PFromJSON(),
                initCardsGridFromJSON(),
                initMarketFromJSON(),
                initLeaderCardsDeckFromJSON(),
                initActionTokenDeckFromJSON()
        );
    }

    /**
     * Method that creates a new faith track for a single player game with the information read by the database.
     */
    private SoloPlayerFaithTrack initFaithTrack1PFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/faithTrack/";
        int numOfSections = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numberOfSections/");
        return new SoloPlayerFaithTrack (getSectionsFromJSON (jsonPath, numOfSections));
    }

    /**
     * Method that creates a new action toke deck for a single player game with the information read by the database.
     */
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

    /**
     * Method that sets a token effect to a solo action token with a key word written into the database.
     */
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

    /**
     * Method that creates a new personal board with the information read by the database.
     */
    public ArrayList<PersonalBoard> loadPersonalBoards(int numOfPlayers) throws FileNotFoundException {
        final String keyInJSON = "personalBoard/";
        int maxNumberOfDevCardsInSlot = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxNumOfDevCardsInSlot/");
        int numberSlotDevCards = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "numOfSlotDevCards/");
        int maxLeaderCardsInSlot = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxNumOfLeaderCardsInSlot/");
        int maxLeaderCardsDuringGame = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, keyInJSON + "maxLeaderCardsDuringGame/");
        int numOfResourcesToPay = (int) jsonHandler.getAsJavaObjectFromJSON (int.class, keyInJSON + "numOfResourcesToPay/");
        int numOfResourcesToProduce = (int) jsonHandler.getAsJavaObjectFromJSON (int.class, keyInJSON + "numOfResourcesToProduce/");
        ArrayList<PersonalBoard> boards = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++)
            boards.add(new PersonalBoard(numOfResourcesToPay, numOfResourcesToProduce, initWarehouseFromJSON(), numberSlotDevCards, maxNumberOfDevCardsInSlot, maxLeaderCardsInSlot, maxLeaderCardsDuringGame));
        return boards;
    }

    /**
     * Method that loads, from the database, the initial parameters to start the game.
     */
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

    /**
     * Method that loads, from the database, the faith track used in a multiplayer game as an array of sections.
     */
    private FaithTrack initFaithTrackFromJSON() throws FileNotFoundException {
        String jsonPath = "gameBoard/faithTrack/";
        int numOfSections = (int) jsonHandler.getAsJavaObjectFromJSON(int.class, jsonPath + "numberOfSections/");
        ArrayList<Section> sections = getSectionsFromJSON(jsonPath, numOfSections);
        return new FaithTrack(sections);
    }

    /**
     *  Method that loads, from the database, an array of sections.
     */
    private ArrayList<Section> getSectionsFromJSON(String jsonPath, int numOfSections) throws FileNotFoundException {
        ArrayList<Section> sections = new ArrayList<>();
        for (int i = 0; i < numOfSections; i++) {
            Section section = (Section) jsonHandler.getAsJavaObjectFromJSONArray(Section.class, jsonPath + "listOfSections/", new int[] {i});
            sections.add(section);
        }
        return sections;
    }

    /**
     * Method that loads, from the database, the market of marbles.
     */
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

    /**
     * Method that loads, from the database, the grid of development cards.
     */
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

    /**
     * Method that loads, from the database, the deck of leader cards.
     */
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
     * @param ints is an index used to pick the information from the json file.
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
                int amountToProduce = (int) jsonHandler.getAsJavaObjectFromJSONArray(int.class, jsonPath + "amountToProduce", ints);
                int amountToPay = (int) jsonHandler.getAsJavaObjectFromJSONArray(int.class, jsonPath + "amountToPay", ints);
                Producible produced = (Producible) jsonHandler.getAsJavaObjectFromJSONArray(Producible.class, jsonPath + "produced", ints);
                card.setExtraProductionPowerEffect(resource, produced, amountToProduce, amountToPay);
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
