package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;
import it.polimi.ingsw.client.view.ui.cli.Colour;

public class SlotDevCardUpdate implements ViewUpdate{

    private int addedDevCard;
    private String description;
    private int numberOfSlot;

    public SlotDevCardUpdate(int addedDevCard, String description, int numberOfSlot) {
        this.addedDevCard = addedDevCard;
        this.description = description;
        this.numberOfSlot = numberOfSlot;
    }

    @Override
    public void update(View view) {
        Colour colour = null;
        int level = 1;
        LWDevCard cardToAdd = new LWDevCard(addedDevCard, description/*, colour, level*/);
        view.getModel().getPersonalBoard().updateSlots(cardToAdd, numberOfSlot);
    }
}
