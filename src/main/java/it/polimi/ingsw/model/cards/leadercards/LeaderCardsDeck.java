package it.polimi.ingsw.model.cards.leadercards;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderCardsDeck {
    private ArrayList <LeaderCard> leaderCardsDeck = new ArrayList<>(0);

    public LeaderCardsDeck(ArrayList<LeaderCard> leaderCardsDeck) {
        this.leaderCardsDeck = leaderCardsDeck;
    }

    LeaderCard getLeaderCard(int index) {
        LeaderCard card = this.leaderCardsDeck.get(index);
        this.leaderCardsDeck.remove(index);
        return card;
    }

    void shuffleDeck() {
        Collections.shuffle(this.leaderCardsDeck);
    }
}
