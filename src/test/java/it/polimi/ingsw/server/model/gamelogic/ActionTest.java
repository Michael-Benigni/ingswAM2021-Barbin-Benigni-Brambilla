package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.exception.TooManyPlayersException;
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
        FaithTrack faithTrack = faithTrackTest.getFaithTrack();
        marketTrayTest.initMarbles();
        MarketTray marketTray = new MarketTray(4, 3, marketTrayTest.marbles);
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
        LeaderCard extraDepotCard = new LeaderCard(requirements, victoryPoints, null);
        extraDepotCard.setExtraDepotEffect(resourceType, capacity);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        leaderCardsList.add(extraDepotCard);
        return leaderCardsList;
    }
}