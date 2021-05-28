package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class RemoveShowGridUpdate {

    private int cardToRemove;
    private int cardToShow;

    public RemoveShowGridUpdate(int cardToRemove, int cardToShow) {
        this.cardToRemove = cardToRemove;
        this.cardToShow = cardToShow;
    }

    public void update(View clientView){
        clientView.getModel().getBoard().updateCardsGrid(cardToRemove, cardToShow);
    }
}
