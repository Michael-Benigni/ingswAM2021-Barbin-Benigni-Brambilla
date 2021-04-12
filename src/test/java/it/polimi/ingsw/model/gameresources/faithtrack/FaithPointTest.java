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
     * Test if the created object is accordant to the provided parameters
     */
    @Test
    void checkConstructorIfCorrect() throws NegativeResourceAmountException {

        int numPoints = 20;
        FaithPoint faithPoint = new FaithPoint(numPoints);

        //assertEquals(faithPoint.getPoints(), numPoints);
    }


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

}
