package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWDepot;
import java.util.ArrayList;

public class WarehouseUpdate implements ViewUpdate {

    private ArrayList<LWDepot> depots;

    public WarehouseUpdate(ArrayList<LWDepot> depots) {
        this.depots = depots;
    }

    @Override
    public void update(Controller clientController){
        clientController.getModel().getPersonalBoard().updateWarehouse(depots);
    }
}
