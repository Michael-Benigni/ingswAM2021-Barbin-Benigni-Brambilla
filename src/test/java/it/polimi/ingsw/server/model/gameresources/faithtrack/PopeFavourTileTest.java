package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.exception.TileAlreadyActivatedException;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on "PopeFavourTile" class.
 */
public class PopeFavourTileTest {

    /**
     * Test on "activateTile" method of this class.
     * Tests if the method return a correctly number of victory points.
     * @throws NegativeVPAmountException -> can be thrown by constructor method of "VictoryPoint" class.
     */
    @Test
    void checkActivateTileIfCorrect() {
        VictoryPoint vp1 = new VictoryPoint(5);
        PopeFavourTile tile = new PopeFavourTile(vp1);
        try{
            VictoryPoint newVP = tile.activateTile();
            assertTrue(newVP.equals(vp1));
        } catch (TileAlreadyActivatedException e){
            fail();
        }
    }


    /**
     * Test on "activateTile" method of this class.
     * Tests if calling the method twice, it throws successfully the exception "TileAlreadyActivated".
     * @throws NegativeVPAmountException
     */
    @Test
    void checkActivateTileIfAlreadyActivated() {
        VictoryPoint vp1 = new VictoryPoint(28);
        PopeFavourTile tile = new PopeFavourTile(vp1);
        try{
            VictoryPoint newVP = tile.activateTile();
        }catch (TileAlreadyActivatedException e) {
            fail();
        }
        try{
            VictoryPoint newAgainVP = tile.activateTile();
            fail();
        } catch (TileAlreadyActivatedException e) {
            assertTrue(true);
        }
    }
}
