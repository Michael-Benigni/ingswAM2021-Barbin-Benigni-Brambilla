package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWResource;
import it.polimi.ingsw.client.view.lightweightmodel.LWWMPower;

public class AddTransformWMUpdate implements ViewUpdate{

    private final Integer powerWMIndex;
    private final LWResource resourceObtained;

    public AddTransformWMUpdate(Integer powerWMIndex, LWResource resourceObtained) {
        this.powerWMIndex = powerWMIndex;
        this.resourceObtained = resourceObtained;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getPersonalBoard().addWMPowers(new LWWMPower(this.powerWMIndex, this.resourceObtained));
    }
}
