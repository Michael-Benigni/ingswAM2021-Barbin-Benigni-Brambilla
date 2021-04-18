package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotContainedResourceException;

public class TemporaryContainer extends ResourcesContainer {
    @Override
    void removeResource(StorableResource storableResource) throws NegativeResourceAmountException, NotContainedResourceException {
        super.removeResource(storableResource);
    }
}
