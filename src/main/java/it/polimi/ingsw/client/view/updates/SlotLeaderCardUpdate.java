package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWLeaderCard;

import java.util.ArrayList;

public class SlotLeaderCardUpdate implements ViewUpdate {
    private ArrayList<Integer> cardsNotPlayed;
    private ArrayList<String> descriptionsNotPlayed;
    private ArrayList<Integer> cardsPlayed;
    private ArrayList<String> descriptionsPlayed;

    public SlotLeaderCardUpdate(ArrayList<Integer> cardsNotPlayed, ArrayList<String> descriptionsNotPlayed,
                                ArrayList<Integer> cardsPlayed, ArrayList<String> descriptionsPlayed) {
        this.cardsPlayed = cardsPlayed;
        this.descriptionsPlayed = descriptionsPlayed;
        this.cardsNotPlayed = cardsNotPlayed;
        this.descriptionsNotPlayed = descriptionsNotPlayed;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getPersonalBoard().updateLeaderCards(buildLWLeaderCards(this.cardsNotPlayed,
                this.descriptionsNotPlayed), buildLWLeaderCards(this.cardsPlayed, this.descriptionsPlayed));
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