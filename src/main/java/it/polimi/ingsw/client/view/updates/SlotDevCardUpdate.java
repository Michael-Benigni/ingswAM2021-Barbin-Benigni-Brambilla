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

    public SlotDevCardUpdate(int addedDevCard, String description, int numberOfSlot, int level, Colour colour) {
        this.addedDevCard = addedDevCard;
        this.description = description;
        this.numberOfSlot = numberOfSlot;
        this.level = level;
        this.colour = colour;
    }

    @Override
    public void update(Controller controller) {
        LWDevCard cardToAdd = new LWDevCard(addedDevCard, description, colour, level);
        controller.getModel().getPersonalBoard().updateSlots(cardToAdd, numberOfSlot);
    }
}
