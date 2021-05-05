package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.cards.developmentcards.*;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gamelogic.GameFactory;
import it.polimi.ingsw.model.gamelogic.actions.GameBoard;
import it.polimi.ingsw.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import it.polimi.ingsw.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.model.gameresources.stores.WarehouseDepots;
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

    @BeforeEach
    public void prepareTests() throws IllegalNumberOfPlayersException, TooManyPlayersException, UserAlreadyPresentInThisGame {
        GameFactory gameFactory = new GameFactory();
        game = gameFactory.MultiOrSingleplayerGame(4);
        player = game.createPlayer();
        auxPlayer = game.createPlayer();
        ArrayList<Integer> capacities = new ArrayList<>();
        capacities.add(3);
        capacities.add(3);
        capacities.add(3);
        WarehouseDepots warehouseDepots = new WarehouseDepots(3, capacities);
        PersonalBoard personalBoard1 = new PersonalBoard(warehouseDepots, 3, 3, 2);
        PersonalBoard personalBoard2 = new PersonalBoard(warehouseDepots, 3, 3, 2);
        player.buildBoard(personalBoard1);
        auxPlayer.buildBoard(personalBoard2);
    }

    @Test
    void onDiscardedTest1() throws EmptySlotException, NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, CloneNotSupportedException, WrongSlotDevelopmentIndexException {
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
        leaderCard.play(player, game);
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
        leaderCard.play(player, null);
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
        leaderCard.play(player, game);
        assertFalse(activated);
    }

    @Test
    void checkDiscountEffect() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException, IllegalNumberOfPlayersException, NegativeVPAmountException, EmptyDeckException, TooManyPlayersException, UserAlreadyPresentInThisGame, ResourceOverflowInDepotException {
        LeaderCard leaderCard = buildDiscountCard();
        ArrayList<DevelopmentCard> cardsList = DevelopmentCardsGridTest.buildCardsForGrid();
        DevelopmentCardsGrid developmentCardsGrid = new DevelopmentCardsGrid(cardsList, 2, 3);
        GameBoard gameBoard = new GameBoard(null, developmentCardsGrid, null, null);
        game.setup(player.getPersonalBoard(), gameBoard);
        game.setup(auxPlayer.getPersonalBoard(), gameBoard);
        leaderCard.play(player, game);
        ArrayList<StorableResource> cost = developmentCardsGrid.getChoosenCard(0, 0, player).getCost();
        ArrayList<StorableResource> cost2 = developmentCardsGrid.getChoosenCard(0, 0, auxPlayer).getCost();
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
    void checkExtraDepotEffect() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException, ResourceOverflowInDepotException {
        LeaderCard leaderCard = buildExtraDepotCard();
        StorableResource resourceToStore = new StorableResource(ResourceType.COIN, 1);
        try{
            player.getPersonalBoard().getWarehouseDepots().store(resourceToStore, 3);
        }
        catch (IndexOutOfBoundsException | WrongDepotIndexException e){
            assertTrue(true);
        }
        leaderCard.play(player, game);
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
        leaderCard.play(player, game);
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
        leaderCard1.play(player, game);
        leaderCard2.play(player, game);
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
        LeaderCard leaderCard = new LeaderCard(requirements, victoryPoints, null);
        leaderCard.setWhiteMarbleTransformationEffect(resourceForWhiteMarble);
        return leaderCard;
    }

    LeaderCard buildExtraProductionCard() throws NegativeResourceAmountException {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        StorableResource resource = new StorableResource(ResourceType.COIN, 1);
        LeaderCard leaderCard = new LeaderCard(requirements, victoryPoints, null);
        leaderCard.setExtraProductionPowerEffect(resource);
        return leaderCard;
    }

    LeaderCard buildExtraDepotCard() throws NegativeResourceAmountException {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        ResourceType resourceType = ResourceType.COIN;
        LeaderCard leaderCard = new LeaderCard(requirements, victoryPoints, null);
        leaderCard.setExtraDepotEffect(resourceType, 2);
        return leaderCard;
    }

    LeaderCard buildLeaderCardWithEffect(){
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        Effect effect = (player, game) -> activated = true;
        LeaderCard leaderCard = new LeaderCard(requirements, victoryPoints, effect);
        return leaderCard;
    }

    LeaderCard buildNotDiscardableCard() throws EmptySlotException, NoEmptyResourceException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, CloneNotSupportedException, WrongSlotDevelopmentIndexException, ResourceOverflowInDepotException {
        Player player = new Player();
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        Effect effect = (player2, game2) -> {};
        LeaderCard leaderCard = new LeaderCard(requirements, victoryPoints, effect);
        leaderCard.play(player, null);
        return leaderCard;
    }

    LeaderCard buildDiscardableCard(){
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        Effect effect = (player, game) -> {};
        LeaderCard leaderCard = new LeaderCard(requirements, victoryPoints, effect);
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
        LeaderCard leaderCard = new LeaderCard(requirements, victoryPoints, effect);
        return leaderCard;
    }

    LeaderCard buildDiscountCard() throws NegativeResourceAmountException {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        VictoryPoint victoryPoints = new VictoryPoint(1);
        StorableResource discount = new StorableResource(ResourceType.SERVANT, 1);
        LeaderCard leaderCard = new LeaderCard(requirements, victoryPoints, null);
        leaderCard.setDiscountEffect(discount);
        return leaderCard;
    }
}