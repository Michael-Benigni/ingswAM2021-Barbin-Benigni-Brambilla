package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.exception.GameOverByFaithTrackException;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.stores.WarehouseDepots;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests on "FaithTrack" class.
 */
public class FaithTrackTest {

    private FaithTrack faithTrack;
    private ClassicSection section1;
    private ClassicCell cell1;
    private VPCell cell2;
    private ClassicCell cell3;
    private VaticanReportSection section2;
    private ClassicCell cell4;
    private PopeSpace cell5;
    private ArrayList<Player> listOfPlayers;
    private ArrayList<Section> listOfSections;

    /**
     * Method that initializes the necessary elements to run the tests below.
     */
    @BeforeEach
    void createFaithTrack() {
        initFaithTrack();
    }

    public FaithTrack initFaithTrack() {
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
        for (int i = 0; i < 15; i++) {
            listOfCells1.add (new ClassicCell ());
            listOfCells2.add (new ClassicCell ());
        }
        listOfSections = new ArrayList<>();
        section1 = new ClassicSection(listOfCells1);
        section2 = new VaticanReportSection(listOfCells2);
        listOfSections.add(section1);
        listOfSections.add(section2);
        int numOfResourcesForProduction = 2;
        PersonalBoard pb = new PersonalBoard(numOfResourcesForProduction, new WarehouseDepots(2, new ArrayList<>(Arrays.asList(2, 3))), 3, 3, 4, 2);
        listOfPlayers = new ArrayList<>(0);
        Player player1 = new Player();
        listOfPlayers.add(player1);
        Player player2 = new Player();
        listOfPlayers.add(player2);
        Player player3 = new Player();
        listOfPlayers.add(player3);
        for(Player p: listOfPlayers)
            p.buildBoard(pb);
        faithTrack = new FaithTrack(listOfSections);
        faithTrack.initMarkers(listOfPlayers);
        return new FaithTrack(listOfSections);
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
        VictoryPoint pointsPlayer1 = listOfPlayers.get(0).computeAllVP();
        VictoryPoint pointsToBeCompared = new VictoryPoint(6);
        assertEquals(pointsPlayer1, pointsToBeCompared);

        faithTrack.moveMarkerForward(listOfPlayers.get(0), 2);
        try {
            faithTrack.moveMarkerForward(listOfPlayers.get(1), 4);
        } catch(GameOverByFaithTrackException e) {
            VictoryPoint vpTot = new VictoryPoint(10 + 6);
            assertEquals(listOfPlayers.get(0).computeAllVP(), vpTot);
            assertEquals(listOfPlayers.get(1).computeAllVP(), vpTot);
            assertEquals(listOfPlayers.get(2).computeAllVP(), new VictoryPoint(0));
        }
    }
}
