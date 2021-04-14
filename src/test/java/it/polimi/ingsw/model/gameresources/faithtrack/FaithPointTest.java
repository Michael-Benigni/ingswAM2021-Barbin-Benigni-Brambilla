package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
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

        try{
            FaithPoint faithPoint = new FaithPoint(numPoints);
            fail();
        } catch (NegativeResourceAmountException e){
            assertTrue(true);
        }
    }


    /**
     * Test on "Clone" method.
     * Tests if the method creates a correct copy of the faith point provided.
     * @throws CloneNotSupportedException -> can be thrown by "equals" method of "FaithPoint" class.
     */
    @Test
    void checkCloneIfCorrect() throws CloneNotSupportedException {
        try {
            FaithPoint newFaithPoint = new FaithPoint(5);
            FaithPoint copiedFaithPoint = (FaithPoint) newFaithPoint.clone();
            assertTrue(copiedFaithPoint.equals(copiedFaithPoint));
        } catch (NegativeResourceAmountException e) {
            fail();
        }
    }

}
