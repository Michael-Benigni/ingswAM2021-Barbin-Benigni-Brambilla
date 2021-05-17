package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.cards.leadercards.LeaderCard;
import it.polimi.ingsw.server.model.cards.leadercards.Requirement;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gamelogic.actions.ExtraBoardProductionAction;
import it.polimi.ingsw.server.model.gamelogic.actions.StartProductionAction;
import it.polimi.ingsw.server.model.gamelogic.actions.StrongboxAction;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ExtraBoardProductionActionTest extends ActionTest {

    /*@Test
    void performTest() throws Exception {
        player1.getPersonalBoard().getStrongbox().store(new StorableResource(ResourceType.COIN, 1));
        FaithMarker faithMarker = game.getGameBoard().getFaithTrack().getMapOfFaithMarkers().get(player1);
        Cell cellBefore = game.getGameBoard().getFaithTrack().getMapOfFaithMarkers().get(player1).getCurrentCell();
        ArrayList<Requirement> requirements = new ArrayList<>();
        VictoryPoint victoryPoints = new VictoryPoint(1);
        LeaderCard leaderCard = new LeaderCard(1, requirements, victoryPoints, null);
        leaderCard.setExtraProductionPowerEffect(new StorableResource(ResourceType.COIN, 1));
        leaderCard.play(player1, game);
        StrongboxAction strongboxAction = new StrongboxAction("remove", new StorableResource(ResourceType.COIN, 1));
        ExtraBoardProductionAction extraBoardProductionAction = new ExtraBoardProductionAction(strongboxAction, new StorableResource(ResourceType.STONE, 1), 0);
        StartProductionAction startProductionAction = new StartProductionAction();
        startProductionAction.perform(game, player1);
        extraBoardProductionAction.perform(game, player1);
        assertTrue(cellBefore.equals(game.getGameBoard().getFaithTrack().getMapOfFaithMarkers().get(player1).getCurrentCell()));
        ArrayList<StorableResource> resources = player1.getPersonalBoard().getStrongbox().getAllResources();
        assertTrue(resources.size() == 1);
        assertTrue(resources.get(0).equals(new StorableResource(ResourceType.STONE, 1)));
    }*/
}