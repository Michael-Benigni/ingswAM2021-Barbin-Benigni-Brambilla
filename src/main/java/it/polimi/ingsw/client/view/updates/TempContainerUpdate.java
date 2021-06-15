package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWResource;

import java.util.ArrayList;

public class TempContainerUpdate implements ViewUpdate {

    private ArrayList<LWResource> storableResources;
    private int emptyResourcesAmount;

    public TempContainerUpdate(ArrayList<LWResource> storableResources, int emptyResourcesAmount) {
        this.storableResources = storableResources;
        this.emptyResourcesAmount = emptyResourcesAmount;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getPersonalBoard().updateTemporaryContainer(this.storableResources, this.emptyResourcesAmount);
    }
}
