package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests on the methods of "SoloPlayerFaithTrack" class.
 */
public class SoloPlayerFaithTrackTest {

    /**
     * Method used to build a new faith track used in the singleplayer match.
     * @return an instance of "SoloPlayerFaithTrack" class.
     */
    public static SoloPlayerFaithTrack initSoloPlayerFaithTrack() {
        ArrayList<Cell> listOfCells1 = new ArrayList<>(0);
        ArrayList<Cell> listOfCells2 = new ArrayList<>(0);
        Cell cell1 = new ClassicCell();
        Cell cell2 = new VPCell(new VictoryPoint(6));
        Cell cell3 = new ClassicCell();
        Cell cell4 = new ClassicCell();
        Cell cell5 = new PopeSpace(new PopeFavourTile(new VictoryPoint(10)));
        listOfCells1.add(cell1);
        listOfCells1.add(cell2);
        listOfCells1.add(cell3);
        listOfCells2.add(cell4);
        listOfCells2.add(cell5);
        ArrayList<Section> listOfSections = new ArrayList<>();
        Section section1 = new ClassicSection(listOfCells1);
        Section section2 = new VaticanReportSection(listOfCells2);
        listOfSections.add(section1);
        listOfSections.add(section2);
        SoloPlayerFaithTrack soloPlayerFaithTrack = new SoloPlayerFaithTrack(listOfSections);
        ArrayList<Player> players = new ArrayList<>(0);
        players.add(new Player());
        soloPlayerFaithTrack.initMarkers(players);
        return soloPlayerFaithTrack;
    }

    /**
     * Test on "moveBlackCross" method of this class.
     * It tests if the method move correctly the black cross. It also tests if the method "getBlackCrossPosition" works successfully.
     */
    @Test
    void checkMoveBlackCrossIfCorrect() throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException, NegativeVPAmountException, GameOverBlackCrossAtEndOfFaithTrackException {
        SoloPlayerFaithTrack faithTrack = initSoloPlayerFaithTrack();
        faithTrack.moveBlackCross(2);
        assertEquals(faithTrack.getBlackCrossPosition(), new ClassicCell());
    }
}
