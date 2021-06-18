package it.polimi.ingsw.server.model.exception;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ExceptionTest {

    @Disabled
    @Test
    void checkMessage() throws Exception {
        throw new IsNotCurrentPlayerException("Pippo");
    }

}