package it.polimi.ingsw.model.gameresources.faithtrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests on "FaithPoint" class
 */
public class FaithPointTest {

        /**
     * Test on constructor method
     * Test if the method throws the NegativeResourceAmountException successfully
     */
    @Test
    void checkConstructorIfNegativeAmount()
    {
        int numPoints = -3;
        FaithPoint faithPoint = new FaithPoint(numPoints);
    }


    /**
     * Test on "Clone" method.
     * Tests if the method creates a correct copy of the faith point provided.
     */
    @Test
    void checkCloneIfCorrect() {
        FaithPoint newFaithPoint = new FaithPoint(5);
        FaithPoint copiedFaithPoint = (FaithPoint) newFaithPoint.clone();
        assertTrue(copiedFaithPoint.equals(copiedFaithPoint));
    }
}
