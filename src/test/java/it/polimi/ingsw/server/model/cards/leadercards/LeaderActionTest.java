package it.polimi.ingsw.server.model.cards.leadercards;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gamelogic.actions.LeaderAction;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderActionTest extends ActionTest {

    @Test
    void performTest1() throws Exception {
        LeaderAction discard1 = new LeaderAction("discard", 1);
        LeaderAction play1 = new LeaderAction("play", 1);
        LeaderAction discard0 = new LeaderAction("discard", 0);
        LeaderAction play2 = new LeaderAction("play", 2);
        LeaderAction play3 = new LeaderAction("play", 3);
        try {
            play1.perform(game, player1);
            fail ();
        } catch (LeaderCardNotPlayedException e) {
            assertTrue (true);
        }
        try {
            discard1.perform(game, player1);
            assertTrue (true);
        }
        catch (LeaderCardNotDiscardableException e){
            fail ();
        }

        try {
            play2.perform(game, player1);
            fail ();
        } catch (LeaderCardNotPlayedException e) {
            assertTrue (true);
        }
        try {
            discard0.perform(game, player1);
            assertTrue (true);
        }
        catch (LeaderCardNotDiscardableException e){
            fail ();
        }

        try{
            play3.perform(game, player1);
            fail();
        }
        catch (IndexOutOfBoundsException e){
            assertTrue(true);
        }

    }

    @Test
    void performTest2() throws NoEmptyResourceException, NegativeResourceAmountException, GameOverByFaithTrackException, LeaderCardNotDiscardableException, WrongSlotDevelopmentIndexException, LeaderCardNotFoundException, EmptySlotException, WrongCellIndexException, CellNotFoundInFaithTrackException, NotEqualResourceTypeException, ResourceOverflowInDepotException, NegativeVPAmountException, NullResourceAmountException, NoValidActionException {
        LeaderAction play0 = new LeaderAction("play", 0);
        LeaderAction play1 = new LeaderAction("play", 1);
        LeaderAction play2 = new LeaderAction("play", 2);
        LeaderAction play3 = new LeaderAction("play", 3);
        try {
            play0.perform(game, player1);
            fail ();
        } catch (LeaderCardNotPlayedException e) {
            assertTrue (true);
        }
        try {
            play1.perform(game, player1);
            fail ();
        } catch (LeaderCardNotPlayedException e) {
            assertTrue (true);
        }
        try {
            play2.perform(game, player1);
            fail ();
        } catch (LeaderCardNotPlayedException e) {
            assertTrue (true);
        }
        try {
            play3.perform(game, player1);
            fail ();
        } catch (LeaderCardNotPlayedException e) {
            assertTrue (true);
        }
        ArrayList<LeaderCard> activatedCards = player1.getPersonalBoard().getSlotLeaderCards().getAllPlayedCards();
        assertEquals (0, activatedCards.size ());
    }
}