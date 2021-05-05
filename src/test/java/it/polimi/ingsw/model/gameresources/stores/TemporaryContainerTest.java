package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.model.cards.leadercards.LeaderCardTest;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class TemporaryContainerTest {
    LeaderCardTest leaderCardTest = new LeaderCardTest();
    @Test
    void checkTransformWhiteMarbleEffect1() throws NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, ResourceOverflowInDepotException, NullResourceAmountException, WrongSlotDevelopmentIndexException, IllegalNumberOfPlayersException, TooManyPlayersException, UserAlreadyPresentInThisGame, NotHaveThisEffectException {
        LeaderCard leaderCard = LeaderCardTest.buildTransformWhiteMarbleCard();
        leaderCardTest.prepareTests();
        try{
            LeaderCardTest.player.getPersonalBoard().getTempContainer().transformEmptyResources(LeaderCardTest.player, 0);
            fail();
        }
        catch (NotHaveThisEffectException e){
            assertTrue(true);
        }
        leaderCard.play(LeaderCardTest.player, LeaderCardTest.game);
        try{
            LeaderCardTest.player.getPersonalBoard().getTempContainer().transformEmptyResources(LeaderCardTest.player, 0);
            fail();
        }
        catch (NoEmptyResourceException e){
            assertTrue(true);
        }
        EmptyResource emptyResource = new EmptyResource();
        LeaderCardTest.player.getPersonalBoard().getTempContainer().store(emptyResource);
        try{
            LeaderCardTest.player.getPersonalBoard().getTempContainer().transformEmptyResources(LeaderCardTest.player, 0);
            assertTrue(true);
        }
        catch (NotHaveThisEffectException | NoEmptyResourceException e){
            fail();
        }
        try{
            LeaderCardTest.auxPlayer.getPersonalBoard().getTempContainer().transformEmptyResources(LeaderCardTest.auxPlayer, 0);
            fail();
        }
        catch(NotHaveThisEffectException e){
            assertTrue(true);
        }
    }

    @Test
    void checkTransformWhiteMarbleEffect2() throws NegativeResourceAmountException, IllegalNumberOfPlayersException, TooManyPlayersException, UserAlreadyPresentInThisGame, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, ResourceOverflowInDepotException, NullResourceAmountException, WrongSlotDevelopmentIndexException, NotHaveThisEffectException {
        LeaderCard leaderCard = LeaderCardTest.buildTransformWhiteMarbleCard();
        leaderCardTest.prepareTests();
        leaderCard.play(LeaderCardTest.player, LeaderCardTest.game);
        EmptyResource emptyResource = new EmptyResource();
        LeaderCardTest.player.getPersonalBoard().getTempContainer().store(emptyResource);
        LeaderCardTest.auxPlayer.getPersonalBoard().getTempContainer().store(emptyResource);
        LeaderCardTest.player.getPersonalBoard().getTempContainer().transformEmptyResources(LeaderCardTest.player, 0);
        ArrayList<StorableResource> resourcesFromPlayer = LeaderCardTest.player.getPersonalBoard().getTempContainer().getAllResources();
        ArrayList<StorableResource> resourcesFromAuxPlayer = LeaderCardTest.auxPlayer.getPersonalBoard().getTempContainer().getAllResources();
        assertTrue(resourcesFromPlayer.get(0).equals(LeaderCardTest.resourceForWhiteMarble));
        assertTrue(resourcesFromAuxPlayer.isEmpty());
    }

    @Test
    void clear() throws NegativeResourceAmountException, IllegalNumberOfPlayersException, TooManyPlayersException, UserAlreadyPresentInThisGame, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, ResourceOverflowInDepotException, NullResourceAmountException, WrongSlotDevelopmentIndexException {
        LeaderCard leaderCard = LeaderCardTest.buildTransformWhiteMarbleCard();
        leaderCardTest.prepareTests();
        StorableResource coin = new StorableResource(ResourceType.COIN, 5);
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 3);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 2);
        EmptyResource emptyResource = new EmptyResource();
        TemporaryContainer temporaryContainer = new TemporaryContainer();
        temporaryContainer.store(emptyResource);
        temporaryContainer.store(coin);
        temporaryContainer.store(servant);
        temporaryContainer.store(shield);
        leaderCard.play(LeaderCardTest.player, LeaderCardTest.game);
        ArrayList<StorableResource> allResources = temporaryContainer.getAllResources();
        if(allResources.size() == 0)
            fail();
        temporaryContainer.clear();
        allResources.clear();
        allResources = temporaryContainer.getAllResources();
        if(allResources.size() != 0)
            fail();
        try{
            temporaryContainer.transformEmptyResources(LeaderCardTest.player, 0);
        }
        catch(NotHaveThisEffectException e){

        }
        catch (NoEmptyResourceException e) {
            assertTrue(true);
        }
    }

    @Test
    void getPenaltyTest1() throws NegativeResourceAmountException {
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        EmptyResource emptyResource = new EmptyResource();
        TemporaryContainer temporaryContainer = new TemporaryContainer();
        temporaryContainer.store(emptyResource);
        temporaryContainer.store(coin);
        temporaryContainer.store(servant);
        temporaryContainer.store(shield);
        FaithPoint faithPoint = new FaithPoint(3);
        FaithPoint penaltyFaithPoint = temporaryContainer.getPenalty();
        assertTrue(faithPoint.equals(penaltyFaithPoint));
    }

    @Test
    void getPenaltyTest2() throws NegativeResourceAmountException {
        TemporaryContainer temporaryContainer = new TemporaryContainer();
        FaithPoint faithPoint = new FaithPoint(0);
        FaithPoint penaltyFaithPoint = temporaryContainer.getPenalty();
        assertTrue(faithPoint.equals(penaltyFaithPoint));
    }

    @Test
    void transformEmptyResources() throws IllegalNumberOfPlayersException, TooManyPlayersException, UserAlreadyPresentInThisGame, NegativeResourceAmountException, EmptySlotException, NoEmptyResourceException, NotEqualResourceTypeException, ResourceOverflowInDepotException, NullResourceAmountException, WrongSlotDevelopmentIndexException, NotHaveThisEffectException {
        LeaderCard leaderCard = LeaderCardTest.buildTransformWhiteMarbleCard();
        leaderCardTest.prepareTests();
        leaderCard.play(LeaderCardTest.player, LeaderCardTest.game);
        EmptyResource emptyResource = new EmptyResource();
        LeaderCardTest.player.getPersonalBoard().getTempContainer().store(emptyResource);
        LeaderCardTest.player.getPersonalBoard().getTempContainer().transformEmptyResources(LeaderCardTest.player, 0);
        StorableResource warehouseDepotsResources = LeaderCardTest.player.getPersonalBoard().getTempContainer().getAllResources().get(0);
        assertTrue(warehouseDepotsResources.equals(LeaderCardTest.resourceForWhiteMarble));
    }
}