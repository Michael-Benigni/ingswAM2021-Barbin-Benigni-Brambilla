package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;
import it.polimi.ingsw.client.view.ui.cli.Colour;

public class RemoveShowGridUpdate implements ViewUpdate{

    private final int cardToRemove;
    private final String removeDescription;
    private final Integer cardToShow;
    private final String showDescription;
    private final Colour colourCardToRemove;
    private final int levelCardToRemove;
    private final Colour colourCardToShow;
    private final int levelCardToShow;

    public RemoveShowGridUpdate(int cardToRemove, String removeDescription, Integer cardToShow,
                                String showDescription, Colour colourCardToRemove, int levelCardToRemove,
                                Colour colourCardToShow, int levelCardToShow) {
        this.cardToRemove = cardToRemove;
        this.removeDescription = removeDescription;
        this.cardToShow = cardToShow;
        this.showDescription = showDescription;
        this.colourCardToRemove = colourCardToRemove;
        this.levelCardToRemove = levelCardToRemove;
        this.colourCardToShow = colourCardToShow;
        this.levelCardToShow = levelCardToShow;
    }

    @Override
    public void update(Controller clientController){
        LWDevCard cardRemoved = new LWDevCard(cardToRemove, removeDescription, colourCardToRemove, levelCardToRemove, -1);
        LWDevCard cardShown = new LWDevCard(cardToShow, showDescription, colourCardToShow, levelCardToShow, -1);
        clientController.getModel().getBoard().getGrid ().update (cardRemoved, cardShown);
    }
}
