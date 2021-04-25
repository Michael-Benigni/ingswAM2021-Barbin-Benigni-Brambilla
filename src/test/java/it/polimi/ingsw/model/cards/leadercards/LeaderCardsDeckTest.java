package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.model.VictoryPoint;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardsDeckTest {

    @Test
    void getLeaderCard() throws NegativeResourceAmountException, NegativeVPAmountException {
        ArrayList <Requirement> requirements = new ArrayList<>(0);
        StorableResource resource = new StorableResource(ResourceType.COIN, 1);
        requirements.add(resource);
        Effect effect = null;
        LeaderCard leaderCard1 = new LeaderCard(requirements, new VictoryPoint(1), effect);
        ArrayList <LeaderCard> listOfLeaderCards = new ArrayList<>(0);
        listOfLeaderCards.add(leaderCard1);
        LeaderCardsDeck leaderCardsDeck = new LeaderCardsDeck(listOfLeaderCards);
        assertEquals(leaderCardsDeck.getLeaderCard(0), leaderCard1);
    }
}