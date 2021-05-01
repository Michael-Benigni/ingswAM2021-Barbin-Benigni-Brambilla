package it.polimi.ingsw.model.gamelogic.actions;

import it.polimi.ingsw.exception.NegativeVPAmountException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on the "VictoryPoint" class.
 */
public class VictoryPointTest {

    /**
     * Test on constructor method.
     * Tests if the method throws successfully the exception when the provided amount is less than zero.
     */
    @Test
    void checkConstructorIfNegativeAmount() {
        VictoryPoint newVictoryPoint = new VictoryPoint(-3);
        assertEquals(new VictoryPoint(0), newVictoryPoint);
    }


    /**
     * Test on "increaseVictoryPoints" method.
     * Tests if the method works successfully.
     */
    @Test
    void checkIncreaseVictoryPointsIfCorrect() {
        VictoryPoint pointsToAdd1 = new VictoryPoint(3);
        VictoryPoint pointsToAdd2 = new VictoryPoint(14);
        VictoryPoint totalPoints = new VictoryPoint(3 + 14);
        pointsToAdd1.increaseVictoryPoints(pointsToAdd2);
        assertEquals(totalPoints, pointsToAdd1);
    }


    /**
     * Test on "decreaseVictoryPoints" method.
     * Tests if the method works successfully.
     */
    @Test
    void checkDecreaseVictoryPointsIfCorrect() {
        try {
            VictoryPoint newVictoryPoint = new VictoryPoint(24);
            VictoryPoint pointsToBeSubtracted = new VictoryPoint(15);
            VictoryPoint totalPoint = new VictoryPoint(24 - 15);
            newVictoryPoint.decreaseVictoryPoints(pointsToBeSubtracted);
            assertEquals(totalPoint, newVictoryPoint);
        } catch (NegativeVPAmountException e) {
            fail();
        }
    }


    /**
     * Test on "decreaseVictoryPoints" method.
     * Tests if the method throws successfully the exception when the victory points to decrease has amount less than
     * the one to be subtracted.
     * @throws NegativeVPAmountException can be thrown by constructor method of this class.
     */
    @Test
    void checkDecreaseVictoryPointsIfNegativeAmount() throws NegativeVPAmountException {
        VictoryPoint newVictoryPoint1 = new VictoryPoint(6);
        VictoryPoint newVictoryPoint2 = new VictoryPoint(6);
        VictoryPoint pointsToBeSubtracted = new VictoryPoint(30);
        try {
            newVictoryPoint1.decreaseVictoryPoints(pointsToBeSubtracted);
            fail();
        } catch (NegativeVPAmountException e) {
            assertEquals(newVictoryPoint2, newVictoryPoint1);
        }
    }


    /**
     * Test on "clone" method of this class.
     * It tests if the method returns a correct copy of the provided victory point.
     */
    @Test
    void checkCloneIfCorrect() {
        VictoryPoint vp1 = new VictoryPoint(12);
        VictoryPoint vp2 = (VictoryPoint) vp1.clone();
        assertEquals(vp1, vp2);
    }
}
