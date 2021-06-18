package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ExceptionTest {

    @Disabled
    @Test
    void checkMessage() throws Exception {
        throw new NotEqualResourceTypeException(ResourceType.SERVANT, ResourceType.STONE);
    }

}