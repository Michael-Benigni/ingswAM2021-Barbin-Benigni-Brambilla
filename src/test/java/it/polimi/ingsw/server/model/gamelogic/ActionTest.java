package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGridTest;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.server.model.cards.leadercards.Requirement;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.faithtrack.*;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTrayTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;

public class ActionTest {
    public Game game;
    MarketTrayTest marketTrayTest = new MarketTrayTest();
    FaithTrackTest faithTrackTest = new FaithTrackTest();
    public Player player1, player2;
    public ArrayList<Integer> capacities = new ArrayList<>();
    private int cardID = 1;

    @BeforeEach
    void initPayTest() throws IllegalNumberOfPlayersException, TooManyPlayersException, NegativeResourceAmountException {
        init();
    }

    public void init() throws NegativeResourceAmountException, IllegalNumberOfPlayersException, TooManyPlayersException {
        capacities.add(1);
        capacities.add(2);
        capacities.add(3);
        WarehouseDepots depots = new WarehouseDepots(3, capacities);
        ArrayList<DevelopmentCard> cardsList = DevelopmentCardsGridTest.buildCardsForGrid();
        DevelopmentCardsGrid developmentCardsGrid = new DevelopmentCardsGrid(cardsList, 3, 4);
        FaithTrack faithTrack = faithTrackTest.initFaithTrack();
        MarketTray marketTray = MarketTrayTest.initMarketTray();
        ArrayList<LeaderCard> leaderCardsList = buildLeaderCards();
        LeaderCardsDeck leaderCardsDeck = new LeaderCardsDeck(leaderCardsList);
        GameBoard gameBoard = new GameBoard(faithTrack, developmentCardsGrid, marketTray, leaderCardsDeck);
        GameFactory gameFactory = new GameFactory();
        game = gameFactory.MultiOrSingleplayerGame(2);
        player1 = game.createPlayer();
        player2 = game.createPlayer();
        PersonalBoard personalBoard1 = new PersonalBoard(depots,3,3,4, 2);
        PersonalBoard personalBoard2 = new PersonalBoard(depots,3,3,4, 2);
        ArrayList<PersonalBoard> listOfPersonalBoard = new ArrayList<>(0);
        listOfPersonalBoard.add(personalBoard1);
        listOfPersonalBoard.add(personalBoard2);
        game.setup(listOfPersonalBoard, gameBoard, new ArrayList<>());
    }

    private ArrayList<LeaderCard> buildLeaderCards() throws NegativeResourceAmountException {
        ArrayList<LeaderCard> leaderCardsList = new ArrayList<>(0);
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        StorableResource coin = new StorableResource(ResourceType.COIN, 5);
        requirements.add(coin);
        VictoryPoint victoryPoints = new VictoryPoint(3);
        ResourceType resourceType = ResourceType.STONE;
        int capacity = 2;
        LeaderCard extraDepotCard1 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard2 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard3 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard4 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard5 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard6 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard7 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard8 = new LeaderCard(cardID, requirements, victoryPoints, null);
        extraDepotCard1.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard2.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard3.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard4.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard5.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard6.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard7.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard8.setExtraDepotEffect(resourceType, capacity);
        leaderCardsList.add(extraDepotCard1);
        leaderCardsList.add(extraDepotCard2);
        leaderCardsList.add(extraDepotCard3);
        leaderCardsList.add(extraDepotCard4);
        leaderCardsList.add(extraDepotCard5);
        leaderCardsList.add(extraDepotCard6);
        leaderCardsList.add(extraDepotCard7);
        leaderCardsList.add(extraDepotCard8);

        return leaderCardsList;
    }
}