package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

public class InvalidAmountForExtraProductionProducedResource extends Exception {

    public InvalidAmountForExtraProductionProducedResource(StorableResource producedResource, int amount) {
        super("The amount of the produced resource for the Extra Production Power must be " + amount + " and instead has been received " + producedResource );
    }
}
