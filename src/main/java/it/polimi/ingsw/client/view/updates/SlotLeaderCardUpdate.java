package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWLeaderCard;

import java.util.ArrayList;

public class SlotLeaderCardUpdate implements ViewUpdate {
    private final ArrayList<Integer> cardsNotPlayed;
    private final ArrayList<Integer> cardsNotPlayedIndexes;
    private final ArrayList<String> descriptionsNotPlayed;
    private final ArrayList<Integer> cardsPlayed;
    private final ArrayList<String> descriptionsPlayed;
    private final ArrayList<Integer> cardsPlayedIndexes;

    public SlotLeaderCardUpdate(ArrayList<Integer> cardsNotPlayed, ArrayList<Integer> cardsNotPlayedIndexes, ArrayList<String> descriptionsNotPlayed,
                                ArrayList<Integer> cardsPlayed, ArrayList<String> descriptionsPlayed, ArrayList<Integer> cardsPlayedIndexes) {
        this.cardsNotPlayedIndexes = cardsNotPlayedIndexes;
        this.cardsPlayed = cardsPlayed;
        this.descriptionsPlayed = descriptionsPlayed;
        this.cardsNotPlayed = cardsNotPlayed;
        this.descriptionsNotPlayed = descriptionsNotPlayed;
        this.cardsPlayedIndexes = cardsPlayedIndexes;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getPersonalBoard().updateLeaderCards(buildLWLeaderCards(this.cardsNotPlayed,
                this.descriptionsNotPlayed, this.cardsNotPlayedIndexes), buildLWLeaderCards(this.cardsPlayed, this.descriptionsPlayed, this.cardsPlayedIndexes));
    }

    private ArrayList<LWLeaderCard> buildLWLeaderCards(ArrayList<Integer> cardsIDs, ArrayList<String> descriptions, ArrayList<Integer> indexes){
        ArrayList<LWLeaderCard> cards = new ArrayList<>();
        for (int i = 0; i < cardsIDs.size(); i++){
            LWLeaderCard newCard = new LWLeaderCard(cardsIDs.get(i), descriptions.get(i), indexes.get (i));
            cards.add(newCard);
        }
        return cards;
    }
}