package it.polimi.ingsw.model.gameresources.stores;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Tests on the "Strongbox" class.
 */
public class StrongboxTest {

    /**
     * Test on the constructor method.
     * Test if the created object contains an empty array.
     */
    @Test
    void checkConstructorIfCorrect()
    {
        Strongbox newStrongbox = new Strongbox();
        assertInstanceOf(Strongbox.class, newStrongbox);
        assertEquals(newStrongbox.getAllResources(), new ArrayList<>(0));
    }
}
