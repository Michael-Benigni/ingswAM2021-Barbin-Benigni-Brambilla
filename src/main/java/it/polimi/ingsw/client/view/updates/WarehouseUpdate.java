package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.LWeightPersonalBoard.LWDepot;

import java.util.ArrayList;

public class WarehouseUpdate implements ViewUpdate{

    private ArrayList<LWDepot> depots;

    public WarehouseUpdate(ArrayList<LWDepot> depots) {
        this.depots = depots;
    }

    @Override
    public void update(View clientView){
        clientView.getModel().getPersonalBoard().updateWarehouse(depots);
    }
}