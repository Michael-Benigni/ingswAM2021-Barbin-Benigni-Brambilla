package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWLeaderCard;

import java.util.ArrayList;

public class InitialSlotLeaderCardUpdate implements ViewUpdate {
    private ArrayList<Integer> initialCardsList;
    private ArrayList<String> descriptions;
    private int maxNumberOfCardsDuringGame;

    public InitialSlotLeaderCardUpdate(ArrayList<Integer> initialCardsList, ArrayList<String> descriptions,
                                       int maxNumberOfCardsDuringGame) {
        this.initialCardsList = initialCardsList;
        this.descriptions = descriptions;
        this.maxNumberOfCardsDuringGame = maxNumberOfCardsDuringGame;
    }

    @Override
    public void update(View view) {
        view.getModel().getPersonalBoard().updateLeaderCardsNotPlayed(buildInitialLeaderCards());
    }

    private ArrayList<LWLeaderCard> buildInitialLeaderCards(){
        ArrayList<LWLeaderCard> cards = new ArrayList<>();
        for (int i = 0; i < this.initialCardsList.size(); i++){
            LWLeaderCard newCard = new LWLeaderCard(this.initialCardsList.get(i), this.descriptions.get(i));
            cards.add(newCard);
        }
        return cards;
    }
}