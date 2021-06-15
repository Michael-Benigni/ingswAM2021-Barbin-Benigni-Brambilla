package it.polimi.ingsw.server.model.cards.leadercards;


import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGridTest;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.GameFactory;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoardTest;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithTrackTest;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTrayTest;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderCardTest {
    private boolean activated = false;
    public static Player player;
    public static Player auxPlayer;
    public static Game game;
    public static StorableResource resourceForWhiteMarble;
    private int cardID = 1;

    @BeforeEach
    public void prepareTests() throws IllegalNumberOfPlayersException, TooManyPlayersException, InvalidUserException {
        GameFactory gameFactory = new GameFactory();
        game = gameFactory.MultiOrSingleplayerGame(2);
        player = game.createPlayer();
        auxPlayer = game.createPlayer();
        ArrayList<Integer> capacities = new ArrayList<>();
        capacities.add(3);
        capacities.add(3);
        capacities.add(3);
        WarehouseDepots warehouseDepots = new WarehouseDepots(3, capacities);
        PersonalBoard personalBoard1 = new PersonalBoard(warehouseDepots, 3, 3, 2, 2);
        PersonalBoard personalBoard2 = new PersonalBoard(warehouseDepots, 3, 3, 2, 2);
        player.buildBoard(personalBoard1);
        auxPlayer.buildBoard(personalBoard2);
    }

    @Test
    void onDiscardedTest1() {
        LeaderCard leaderCard = buildDiscardableCard();
        try{
            leaderCard.onDiscarded();
            assertTrue(true);
        }
        catch (LeaderCardNotDiscardableException e){
            fail();
        }
    }

    @Test
    void onDiscardedTest2() throws EmptySlotException, NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, CloneNotSupportedException, WrongSlotDevelopmentIndexException, ResourceOverflowInDepotException {
        LeaderCard leaderCard = buildNotDiscardableCard();
        try{
            leaderCard.onDiscarded();
            fail();
        }
        catch (LeaderCardNotDiscardableException e){
            assertTrue(true);
        }
    }

    @Test
    void playTest() throws EmptySlotException, NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, CloneNotSupportedException, WrongSlotDevelopmentIndexException, ResourceOverflowInDepotException {
        LeaderCard leaderCard = buildLeaderCardWithEffect();
        try {
            leaderCard.play(player, game);
        } catch (LeaderCardNotPlayedException e) {
            fail ();
        }
        assertTrue(activated);
        activated = false;
    }

    @Test
    void checkRequirementsOfTest1() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, NullResourceAmountException, CloneNotSupportedException, WrongSlotDevelopmentIndexException, ResourceOverflowInDepotException {
        LeaderCard leaderCard = buildLeaderCardWithRequirements();
        StorableResource coin = new StorableResource(ResourceType.COIN, 2);
        StorableResource stone = new StorableResource(ResourceType.STONE, 3);
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        ArrayList<StorableResource> resources = new ArrayList<>(0);
        resources.add(coin);
        resources.add(stone);
        resources.add(servant);
        player.getPersonalBoard().getStrongbox().storeAll(resources);
        try {
            leaderCard.play(player, null);
        } catch (LeaderCardNotPlayedException e) {
            fail ();
        }
        assertTrue(activated);
        activated = false;
    }

    @Test
    void checkRequirementsOfTest2() throws EmptySlotException, NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException, ResourceOverflowInDepotException {
        LeaderCard leaderCard = buildLeaderCardWithRequirements();
        StorableResource coin = new StorableResource(ResourceType.COIN, 2);
        StorableResource stone = new StorableResource(ResourceType.STONE, 1);
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        ArrayList<StorableResource> resources = new ArrayList<>(0);
        resources.add(coin);
        resources.add(stone);
        resources.add(servant);
        player.getPersonalBoard().getStrongbox().storeAll(resources);
        try {
            leaderCard.play(player, game);
            fail ();
        } catch (LeaderCardNotPlayedException e) {
            assertTrue (true);
        }
        assertFalse(activated);
    }

    @Test
    void checkDiscountEffect() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException, IllegalNumberOfPlayersException, NegativeVPAmountException, EmptyDeckException, TooManyPlayersException, InvalidUserException, ResourceOverflowInDepotException, WrongBoardException {
        LeaderCard leaderCard = buildDiscountCard();
        ArrayList<DevelopmentCard> cardsList = DevelopmentCardsGridTest.buildCardsForGrid();
        DevelopmentCardsGrid developmentCardsGrid = new DevelopmentCardsGrid(cardsList, 3, 4);
        GameBoard gameBoard = new GameBoard(new FaithTrackTest().initFaithTrack(), developmentCardsGrid, MarketTrayTest.initMarketTray (), new LeaderCardsDeckTest().getLeaderCardsDeck());
        ArrayList<PersonalBoard> personalBoards = new ArrayList<>();
        for (int i = 0; i < 2; i++)
            personalBoards.add(new PersonalBoard(new WarehouseDepots(0, new ArrayList<>()), 4, 3, 4, 2));
        game.setup(personalBoards, gameBoard, new ArrayList<>());
        try {
            leaderCard.play(player, game);
        } catch (LeaderCardNotPlayedException e) {
            fail ();
        }
        ArrayList<StorableResource> cost = developmentCardsGrid.getChosenCard (0, 0, player).getCost();
        ArrayList<StorableResource> cost2 = developmentCardsGrid.getChosenCard (0, 0, auxPlayer).getCost();
        ArrayList<StorableResource> cost1 = new ArrayList<>();
        StorableResource cost11 = new StorableResource(ResourceType.SERVANT, 1);
        cost1.add(cost11);
        for (int i = 0; i < cost1.size(); i++){
            assertEquals(cost.get(i), cost1.get(i));
        }
        for(int i = 0; i < cost1.size(); i++){
            assertFalse(cost2.get(i).equals(cost1.get(i)));
        }
    }

    @Test
    void checkExtraDepotEffect() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException,
            NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException,
            ResourceOverflowInDepotException, SameResourceTypeInDifferentDepotsException {
        LeaderCard leaderCard = buildExtraDepotCard();
        StorableResource resourceToStore = new StorableResource(ResourceType.COIN, 1);
        try{
            player.getPersonalBoard().getWarehouseDepots().store(resourceToStore, 3);
        }
        catch (IndexOutOfBoundsException | WrongDepotIndexException e){
            assertTrue(true);
        }
        try {
            leaderCard.play(player, game);
        } catch (LeaderCardNotPlayedException e) {
            fail ();
        }
        try{
            player.getPersonalBoard().getWarehouseDepots().store(resourceToStore, 3);
        }
        catch (WrongDepotIndexException e){
            fail();
        }
    }

    @Test
    void checkExtraProductionEffect1() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException, NotExistingExtraProductionPower, ResourceOverflowInDepotException {
        LeaderCard leaderCard = buildExtraProductionCard();
        try {
            leaderCard.play(player, game);
        } catch (LeaderCardNotPlayedException e) {
            fail ();
        }
        try{
            player.getPersonalBoard().getExtraPower(0);
        }
        catch(NotExistingExtraProductionPower e){
            fail();
        }
    }

    @Test
    void checkExtraProductionEffect2() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException, NotExistingExtraProductionPower {
        LeaderCard leaderCard = buildExtraProductionCard();
        try{
            player.getPersonalBoard().getExtraPower(0);
        }
        catch(NotExistingExtraProductionPower e){
            assertTrue(true);
        }
    }

    @Test
    void checkExtraProductionEffect3() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException, NotExistingExtraProductionPower, ResourceOverflowInDepotException {
        LeaderCard leaderCard1 = buildExtraProductionCard();
        LeaderCard leaderCard2 = buildExtraProductionCard();
        try {
            leaderCard1.play(player, game);
        } catch (LeaderCardNotPlayedException e) {
            fail ();
        }
        try {
            leaderCard2.play(player, game);
        } catch (LeaderCardNotPlayedException e) {
            fail ();
        }
        try{
            player.getPersonalBoard().getExtraPower(0);
            player.getPersonalBoard().getExtraPower(1);
        }
        catch(NotExistingExtraProductionPower e){
            fail();
        }
    }

    public static LeaderCard buildTransformWhiteMarbleCard() throws NegativeResourceAmountException {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        resourceForWhiteMarble = new StorableResource(ResourceType.COIN, 1);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        LeaderCard leaderCard = new LeaderCard(1, requirements, victoryPoints, null);
        leaderCard.setWhiteMarbleTransformationEffect(resourceForWhiteMarble);
        return leaderCard;
    }

    LeaderCard buildExtraProductionCard() throws NegativeResourceAmountException {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        StorableResource resource = new StorableResource(ResourceType.COIN, 1);
        LeaderCard leaderCard = new LeaderCard(cardID, requirements, victoryPoints, null);
        leaderCard.setExtraProductionPowerEffect(resource);
        return leaderCard;
    }

    LeaderCard buildExtraDepotCard() throws NegativeResourceAmountException {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        ResourceType resourceType = ResourceType.COIN;
        LeaderCard leaderCard = new LeaderCard(cardID, requirements, victoryPoints, null);
        leaderCard.setExtraDepotEffect(resourceType, 2);
        return leaderCard;
    }

    LeaderCard buildLeaderCardWithEffect(){
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        Effect effect = (player, game) -> activated = true;
        LeaderCard leaderCard = new LeaderCard(cardID, requirements, victoryPoints, effect);
        return leaderCard;
    }

    LeaderCard buildNotDiscardableCard() throws EmptySlotException, NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, CloneNotSupportedException, WrongSlotDevelopmentIndexException, ResourceOverflowInDepotException {
        Player player = new Player();
        player.buildBoard (PersonalBoardTest.init ());
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        Effect effect = (player2, game2) -> {};
        LeaderCard leaderCard = new LeaderCard(cardID, requirements, victoryPoints, effect);
        try {
            leaderCard.play(player, game);
        } catch (LeaderCardNotPlayedException e) {
            fail ();
        }
        return leaderCard;
    }

    LeaderCard buildDiscardableCard(){
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        Effect effect = (player, game) -> {};
        LeaderCard leaderCard = new LeaderCard(cardID, requirements, victoryPoints, effect);
        return leaderCard;
    }

    LeaderCard buildLeaderCardWithRequirements() throws NegativeResourceAmountException {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        StorableResource coin = new StorableResource(ResourceType.COIN, 2);
        StorableResource stone = new StorableResource(ResourceType.STONE, 3);
        requirements.add(coin);
        requirements.add(stone);
        VictoryPoint victoryPoints = new VictoryPoint(3);
        Effect effect = (player, game) -> activated = true;
        LeaderCard leaderCard = new LeaderCard(cardID, requirements, victoryPoints, effect);
        return leaderCard;
    }

    LeaderCard buildDiscountCard() throws NegativeResourceAmountException {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        StorableResource discount = new StorableResource(ResourceType.SERVANT, 1);
        LeaderCard leaderCard = new LeaderCard(cardID, requirements, victoryPoints, null);
        leaderCard.setDiscountEffect(discount);
        return leaderCard;
    }
}