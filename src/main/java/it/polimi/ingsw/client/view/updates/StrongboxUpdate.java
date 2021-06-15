package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWResource;

import java.util.ArrayList;

public class StrongboxUpdate implements ViewUpdate{

    private ArrayList<LWResource> strongbox;

    public StrongboxUpdate(ArrayList<LWResource> newStrongbox) {
        this.strongbox = newStrongbox;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getPersonalBoard().updateStrongbox(this.strongbox);
    }


}
