package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;
import it.polimi.ingsw.client.view.ui.cli.Colour;

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
        Colour colour = null;
        int level = 1;
        LWDevCard cardRemove = new LWDevCard(cardToRemove, removeDescription/*, colour, level*/);
        LWDevCard cardShow;
        if(cardToShow != null){
            cardShow = new LWDevCard(cardToShow, showDescription/*, colour, level*/);
        }
        else
            cardShow = null;
        clientView.getModel().getBoard().getGrid ().update (cardRemove, cardShow);
    }
}
