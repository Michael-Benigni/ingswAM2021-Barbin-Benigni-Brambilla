package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.LWProducible;
import it.polimi.ingsw.client.view.lightweightmodel.LWResource;
import it.polimi.ingsw.client.view.lightweightmodel.LWXProductionPower;

public class AddExtraProductionPowerUpdate implements ViewUpdate {

    private final LWResource consumedResource;
    private final Integer numberOfResourceToPay;
    private final Integer numberOfResourceToProduce;
    private final LWProducible produced;
    private final Integer indexOfPower;

    public AddExtraProductionPowerUpdate(LWResource consumedResource, Integer numberOfResourceToPay,
                                         Integer numberOfResourceToProduce, LWProducible produced, Integer indexOfPower) {
        this.consumedResource = consumedResource;
        this.numberOfResourceToPay = numberOfResourceToPay;
        this.numberOfResourceToProduce = numberOfResourceToProduce;
        this.produced = produced;
        this.indexOfPower = indexOfPower;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel().getPersonalBoard().addXProductionPowers(new LWXProductionPower(this.consumedResource,
                this.numberOfResourceToPay, this.numberOfResourceToProduce, this.produced, this.indexOfPower));
    }
}
