package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.exception.GameOverByFaithTrackException;
import it.polimi.ingsw.exception.WrongCellIndexException;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import it.polimi.ingsw.model.gamelogic.actions.VictoryPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on "FaithTrack" class.
 */
public class FaithTrackTest {

    FaithTrack faithTrack;
    ClassicSection section1;
    ClassicCell cell1;
    VPCell cell2;
    ClassicCell cell3;
    VaticanReportSection section2;
    ClassicCell cell4;
    PopeSpace cell5;
    ArrayList<Player> listOfPlayers;

    /**
     * Method that initializes the necessary elements to run the tests below.
     * @throws WrongCellIndexException -> can be thrown by the constructor method of "FaithTrack" class.
     */
    @BeforeEach
    void createFaithTrack() throws WrongCellIndexException {
        ArrayList<Cell> listOfCells1 = new ArrayList<>(0);
        ArrayList<Cell> listOfCells2 = new ArrayList<>(0);
        cell1 = new ClassicCell();
        cell2 = new VPCell(new VictoryPoint(6));
        cell3 = new ClassicCell();
        cell4 = new ClassicCell();
        cell5 = new PopeSpace(new PopeFavourTile(new VictoryPoint(10)));
        listOfCells1.add(cell1);
        listOfCells1.add(cell2);
        listOfCells1.add(cell3);
        listOfCells2.add(cell4);
        listOfCells2.add(cell5);
        section1 = new ClassicSection(listOfCells1);
        section2 = new VaticanReportSection(listOfCells2);
        ArrayList<Section> listOfSections = new ArrayList<>(0);
        listOfSections.add(section1);
        listOfSections.add(section2);
        listOfPlayers = new ArrayList<>(0);
        Player player1 = new Player();
        listOfPlayers.add(player1);
        Player player2 = new Player();
        listOfPlayers.add(player2);
        Player player3 = new Player();
        listOfPlayers.add(player3);
        faithTrack = new FaithTrack(listOfSections, listOfPlayers);
    }


    /**
     * Test on "findSectionOfThisCell" method of this class.
     * It tests if the method returns the right section for each provided cell of the faith track.
     */
    @Test
    void checkFindSectionOfThisCellIfCorrect() {
        try {
            assertEquals(section1, faithTrack.findSectionOfThisCell(cell1));
            assertEquals(section1, faithTrack.findSectionOfThisCell(cell2));
            assertEquals(section1, faithTrack.findSectionOfThisCell(cell3));
            assertEquals(section2, faithTrack.findSectionOfThisCell(cell4));
            assertEquals(section2, faithTrack.findSectionOfThisCell(cell5));
        } catch (CellNotFoundInFaithTrackException e) {
            fail();
        }
    }


    /**
     * Test on "findSectionOfThisCell" method of this class.
     * It tests if the method throws successfully the exception when the provided cell doesn't exist in the faith track.
     */
    @Test
    void checkFindSectionOfThisCellIfNotExist() {
        Cell newCell = new ClassicCell();
        try {
            Section newSection = faithTrack.findSectionOfThisCell(newCell);
            fail();
        } catch (CellNotFoundInFaithTrackException e) {
            assertTrue(true);
        }
    }


    /**
     * Test on "moveMarkerForward" method of this class.
     * It tests if the method successfully updates the amount of victory points of each player. Then it throws the
     * correct exception that finishes the game.
     * @throws Exception -> can be thrown by "moveMarkerForward" method of this class.
     */
    @Test
    void checkMoveMarkerForwardIfCorrect() throws Exception {
        faithTrack.moveMarkerForward(listOfPlayers.get(0), 1);
        VictoryPoint pointsPlayer1 = listOfPlayers.get(0).getVictoryPoints();
        VictoryPoint pointsToBeCompared = new VictoryPoint(6);
        assertEquals(pointsPlayer1, pointsToBeCompared);

        faithTrack.moveMarkerForward(listOfPlayers.get(0), 2);
        try {
            faithTrack.moveMarkerForward(listOfPlayers.get(1), 4);
        } catch(GameOverByFaithTrackException e) {
            VictoryPoint vpTot = new VictoryPoint(10 + 6);
            assertEquals(listOfPlayers.get(0).getVictoryPoints(), vpTot);
            assertEquals(listOfPlayers.get(1).getVictoryPoints(), vpTot);
            assertEquals(listOfPlayers.get(2).getVictoryPoints(), new VictoryPoint(0));
        }
    }
}
