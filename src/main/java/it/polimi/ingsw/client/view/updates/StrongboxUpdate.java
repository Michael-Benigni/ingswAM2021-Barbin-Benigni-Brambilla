package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWeightPersonalBoard;

import java.util.ArrayList;

public class StrongboxUpdate implements ViewUpdate{

    private ArrayList<LWeightPersonalBoard.LWResource> newStrongbox;

    public StrongboxUpdate(ArrayList<LWeightPersonalBoard.LWResource> newStrongbox) {
        this.newStrongbox = newStrongbox;
    }

    @Override
    public void update(View view) {
        view.getModel().getPersonalBoard().updateStrongbox(this.newStrongbox);
    }


}
