package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;

public class RemoveShowGridUpdate implements ViewUpdate{

    private int cardToRemove;
    private String removeDescription;
    private Integer cardToShow;
    private String showDescription;

    public RemoveShowGridUpdate(int cardToRemove, String removeDescription, Integer cardToShow, String showDescription) {
        this.cardToRemove = cardToRemove;
        this.removeDescription = removeDescription;
        this.cardToShow = cardToShow;
        this.showDescription = showDescription;
    }

    @Override
    public void update(View clientView){
        LWDevCard cardRemove = new LWDevCard(cardToRemove, removeDescription);
        LWDevCard cardShow;
        if(cardToShow != null){
            cardShow = new LWDevCard(cardToShow, showDescription);
        }
        else
            cardShow = null;
        clientView.getModel().getBoard().updateCardsGrid(cardRemove, cardShow);
    }
}
