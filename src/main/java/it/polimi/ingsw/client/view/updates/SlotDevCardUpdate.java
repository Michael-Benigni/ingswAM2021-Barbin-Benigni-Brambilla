package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;
import it.polimi.ingsw.client.view.ui.cli.Colour;

public class SlotDevCardUpdate implements ViewUpdate{

    private final int addedDevCard;
    private final String description;
    private final int numberOfSlot;
    private final int level;
    private final Colour colour;
    private final int index;

    public SlotDevCardUpdate(int addedDevCard, String description, int numberOfSlot, int level, Colour colour, int index) {
        this.addedDevCard = addedDevCard;
        this.description = description;
        this.numberOfSlot = numberOfSlot;
        this.level = level;
        this.colour = colour;
        this.index = index;
    }

    @Override
    public void update(Controller controller) {
        LWDevCard cardToAdd = new LWDevCard(addedDevCard, description, colour, level, index);
        controller.getModel().getPersonalBoard().updateSlots(cardToAdd, numberOfSlot);
    }
}
