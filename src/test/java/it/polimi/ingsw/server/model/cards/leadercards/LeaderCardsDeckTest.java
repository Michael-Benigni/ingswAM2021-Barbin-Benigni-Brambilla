package it.polimi.ingsw.server.model.cards.leadercards;

import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderCardsDeckTest {
    private LeaderCardsDeck leaderCardsDeck;
    private LeaderCard leaderCard1;
    private StorableResource resource;
    private ArrayList<LeaderCard> list;

    @Test
    void draw() throws NegativeResourceAmountException {
        assertEquals(leaderCardsDeck.draw(1).get(0), leaderCard1);
    }

    @BeforeEach
    private void initLeaderCardsDeck() throws NegativeResourceAmountException {
        ArrayList <Requirement> requirements = new ArrayList<>(0);
        resource = new StorableResource(ResourceType.COIN, 1);
        requirements.add(resource);
        Effect effect = null;
        leaderCard1 = new LeaderCard(requirements, new VictoryPoint(1), effect);
        list = new ArrayList<>(0);
        list.add(leaderCard1);
        leaderCardsDeck = new LeaderCardsDeck(list);
    }

    public LeaderCardsDeck getLeaderCardsDeck() throws NegativeResourceAmountException {
        initLeaderCardsDeck();
        for (int i = 0; i < 15; i++)
            list.add(leaderCard1);
        return new LeaderCardsDeck(list);
    }
}