package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gameresources.stores.EmptyResource;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TransformWhiteMarbleActionTest extends ActionTest {

    @Test
    void performTest() throws Exception {
        LeaderCard leaderCard = new LeaderCard(1, new ArrayList<>(), new VictoryPoint(1), null);
        StorableResource resource = new StorableResource(ResourceType.COIN, 1);
        leaderCard.setWhiteMarbleTransformationEffect(resource);
        TransformWhiteMarbleAction transformWhiteMarbleAction = new TransformWhiteMarbleAction(0);

        try{
            transformWhiteMarbleAction.perform(game, player1);
            fail();
        }
        catch(NotHaveThisEffectException e){
            assertTrue(true);
        }

        leaderCard.play(player1, game);
        EmptyResource emptyResource = new EmptyResource();
        emptyResource.activate(player1, game);
        transformWhiteMarbleAction.perform(game, player1);
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().size() == 1);
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().get(0).equals(resource));

        try{
            transformWhiteMarbleAction.perform(game, player1);
            fail();
        }
        catch(NoEmptyResourceException e){
            assertTrue(true);
        }

    }
}