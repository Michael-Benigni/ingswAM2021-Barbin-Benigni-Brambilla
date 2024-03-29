package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;
import it.polimi.ingsw.client.view.ui.cli.Colour;

public class SlotDevCardUpdate implements ViewUpdate{

    private final Integer addedDevCard;
    private final String description;
    private final Integer numberOfSlot;
    private final Integer level;
    private final Colour colour;
    private final Integer index;

    public SlotDevCardUpdate(Integer addedDevCard, String description, Integer numberOfSlot, Integer level, Colour colour, Integer index) {
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
        controller.getModel ().getPersonalBoard ().updateSlots (cardToAdd, numberOfSlot, index);
    }
}
