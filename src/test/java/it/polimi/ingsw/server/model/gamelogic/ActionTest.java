package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.*;
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
    public FaithTrackTest faithTrackTest = new FaithTrackTest();
    public Player player1, player2;
    public ArrayList<Integer> capacities = new ArrayList<>();

    @BeforeEach
    void initPayTest() throws IllegalNumberOfPlayersException, TooManyPlayersException, NegativeResourceAmountException, EmptyDeckException, WrongBoardException {
        init();
    }

    public void init() throws NegativeResourceAmountException, IllegalNumberOfPlayersException, TooManyPlayersException, EmptyDeckException, WrongBoardException {
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
        PersonalBoard personalBoard3 = new PersonalBoard(depots,3,3,4, 2);
        PersonalBoard personalBoard4 = new PersonalBoard(depots,3,3,4, 2);
        ArrayList<PersonalBoard> listOfPersonalBoard = new ArrayList<>(0);
        listOfPersonalBoard.add(personalBoard1);
        listOfPersonalBoard.add(personalBoard2);
        listOfPersonalBoard.add (personalBoard3);
        listOfPersonalBoard.add (personalBoard4);
        game.setup(listOfPersonalBoard, gameBoard, new ArrayList<>());
    }

    MultiplayerGame getMultiPlayerGame() {
        return (MultiplayerGame) game;
    }

    private ArrayList<LeaderCard> buildLeaderCards() throws NegativeResourceAmountException {
        ArrayList<LeaderCard> leaderCardsList = new ArrayList<>(0);
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        StorableResource coin = new StorableResource(ResourceType.COIN, 5);
        requirements.add(coin);
        VictoryPoint victoryPoints = new VictoryPoint(3);
        ResourceType resourceType = ResourceType.STONE;
        int capacity = 2;
        int cardID = 1;
        LeaderCard extraDepotCard1 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard2 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard3 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard4 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard5 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard6 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard7 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard8 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard9 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard10 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard11 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard12 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard13 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard14 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard15 = new LeaderCard(cardID, requirements, victoryPoints, null);
        LeaderCard extraDepotCard16 = new LeaderCard(cardID, requirements, victoryPoints, null);
        extraDepotCard1.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard2.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard3.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard4.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard5.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard6.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard7.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard8.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard9.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard10.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard11.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard12.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard13.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard14.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard15.setExtraDepotEffect(resourceType, capacity);
        extraDepotCard16.setExtraDepotEffect(resourceType, capacity);
        leaderCardsList.add(extraDepotCard1);
        leaderCardsList.add(extraDepotCard2);
        leaderCardsList.add(extraDepotCard3);
        leaderCardsList.add(extraDepotCard4);
        leaderCardsList.add(extraDepotCard5);
        leaderCardsList.add(extraDepotCard6);
        leaderCardsList.add(extraDepotCard7);
        leaderCardsList.add(extraDepotCard8);
        leaderCardsList.add(extraDepotCard9);
        leaderCardsList.add(extraDepotCard10);
        leaderCardsList.add(extraDepotCard11);
        leaderCardsList.add(extraDepotCard12);
        leaderCardsList.add(extraDepotCard13);
        leaderCardsList.add(extraDepotCard14);
        leaderCardsList.add(extraDepotCard15);
        leaderCardsList.add(extraDepotCard16);

        return leaderCardsList;
    }
}