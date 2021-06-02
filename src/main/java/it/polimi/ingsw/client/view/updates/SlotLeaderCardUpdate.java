package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWLeaderCard;

import java.util.ArrayList;

public class SlotLeaderCardUpdate implements ViewUpdate {
    private ArrayList<Integer> notPlayedCardsList;
    private ArrayList<String> notPlayedDescriptions;
    private ArrayList<Integer> playedCardsList;
    private ArrayList<String> playedDescriptions;

    public SlotLeaderCardUpdate(ArrayList<Integer> notPlayedCardsList, ArrayList<String> notPlayedDescriptions,
                                ArrayList<Integer> playedCardsList, ArrayList<String> playedDescriptions) {
        this.playedCardsList = playedCardsList;
        this.playedDescriptions = playedDescriptions;
        this.notPlayedCardsList = notPlayedCardsList;
        this.notPlayedDescriptions = notPlayedDescriptions;
    }

    @Override
    public void update(View view) {
        view.getModel().getPersonalBoard().updateLeaderCards(buildLWLeaderCards(this.notPlayedCardsList,
                this.notPlayedDescriptions), buildLWLeaderCards(this.playedCardsList, this.playedDescriptions));
    }

    private ArrayList<LWLeaderCard> buildLWLeaderCards(ArrayList<Integer> cardsIDs, ArrayList<String> descriptions){
        ArrayList<LWLeaderCard> cards = new ArrayList<>();
        for (int i = 0; i < cardsIDs.size(); i++){
            LWLeaderCard newCard = new LWLeaderCard(cardsIDs.get(i), descriptions.get(i));
            cards.add(newCard);
        }
        return cards;
    }
}