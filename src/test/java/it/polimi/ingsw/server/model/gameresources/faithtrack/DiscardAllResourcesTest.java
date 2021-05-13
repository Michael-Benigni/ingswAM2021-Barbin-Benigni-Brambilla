package it.polimi.ingsw.server.model.gameresources.faithtrack;


import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.ActionTest;
import it.polimi.ingsw.server.model.gamelogic.actions.DiscardAllResources;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.server.model.gameresources.stores.TemporaryContainer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DiscardAllResourcesTest extends ActionTest {
    private StorableResource resource1 = new StorableResource(ResourceType.COIN, 2);
    private StorableResource resource2 = new StorableResource(ResourceType.STONE, 1);
    private TemporaryContainer temporaryContainer = new TemporaryContainer();

    DiscardAllResourcesTest() throws NegativeResourceAmountException {
    }

    @Test
    void perform() throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException,
            NegativeVPAmountException {
        player1.getPersonalBoard().getTempContainer().store(resource1);
        player1.getPersonalBoard().getTempContainer().store(resource2);
        DiscardAllResources discardAllResourcesAction = new DiscardAllResources();
        discardAllResourcesAction.perform(game, player1);
        game.getGameBoard().getFaithTrack().moveMarkerForward(player1, 3);
        HashMap hashMap = game.getGameBoard().getFaithTrack().getMapOfFaithMarkers();
        FaithMarker faithMarkerPlayer2 = (FaithMarker) hashMap.get(player2);
        FaithMarker faithMarkerPlayer1 = (FaithMarker) hashMap.get(player1);
        assertTrue(faithMarkerPlayer1.equals(faithMarkerPlayer2));
        assertTrue(player1.getPersonalBoard().getTempContainer().getAllResources().isEmpty());
    }
}