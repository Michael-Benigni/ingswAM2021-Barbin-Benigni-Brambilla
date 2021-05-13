package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.utils.config.JsonHandler;
import it.polimi.ingsw.server.model.exception.WrongCellIndexException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;

import java.io.IOException;
import java.util.ArrayList;

public class FaithTrackWriter {

    public static void buildFaithTrackForDatabase() throws WrongCellIndexException, IOException {
        ArrayList<Section> listSection = new ArrayList<>(0);
        PopeFavourTile tile1 = new PopeFavourTile(new VictoryPoint(2));
        PopeFavourTile tile2 = new PopeFavourTile(new VictoryPoint(3));
        PopeFavourTile tile3 = new PopeFavourTile(new VictoryPoint(4));
        Cell cell = new ClassicCell();
        Cell vp1 = new VPCell(new VictoryPoint(1));
        Cell vp2 = new VPCell(new VictoryPoint(2));
        Cell vp4 = new VPCell(new VictoryPoint(4));
        Cell vp6 = new VPCell(new VictoryPoint(6));
        Cell vp9 = new VPCell(new VictoryPoint(9));
        Cell vp12 = new VPCell(new VictoryPoint(12));
        Cell vp16 = new VPCell(new VictoryPoint(16));
        Cell popeSpace1 = new PopeSpace(tile1);
        Cell popeSpace2 = new PopeSpace(tile2);
        Cell finalPopeSpace = new VictoryPointsPopeSpace(new VictoryPoint(20), tile3);

        ArrayList<Cell> listCell1 = new ArrayList<>(0);
        listCell1.add(cell);
        listCell1.add(cell);
        listCell1.add(cell);
        listCell1.add(vp1);
        listCell1.add(cell);
        Section section1 = new ClassicSection(listCell1);
        listSection.add(section1);

        ArrayList<Cell> listCell2 = new ArrayList<>(0);
        listCell2.add(cell);
        listCell2.add(vp2);
        listCell2.add(cell);
        listCell2.add(popeSpace1);
        Section section2 = new VaticanReportSection(listCell2);
        listSection.add(section2);

        ArrayList<Cell> listCell3 = new ArrayList<>(0);
        listCell3.add(vp4);
        listCell3.add(cell);
        listCell3.add(cell);
        Section section3 = new ClassicSection(listCell3);
        listSection.add(section3);

        ArrayList<Cell> listCell4 = new ArrayList<>(0);
        listCell4.add(vp6);
        listCell4.add(cell);
        listCell4.add(cell);
        listCell4.add(vp9);
        listCell4.add(popeSpace2);
        Section section4 = new VaticanReportSection(listCell4);
        listSection.add(section4);

        ArrayList<Cell> listCell5 = new ArrayList<>(0);
        listCell5.add(cell);
        listCell5.add(vp12);
        Section section5 = new ClassicSection(listCell5);
        listSection.add(section5);

        ArrayList<Cell> listCell6 = new ArrayList<>(0);
        listCell6.add(cell);
        listCell6.add(cell);
        listCell6.add(vp16);
        listCell6.add(cell);
        listCell6.add(cell);
        listCell6.add(finalPopeSpace);
        Section section6 = new VaticanReportSection(listCell6);
        listSection.add(section6);

        ArrayList<Player> players = new ArrayList<>(0);
        players.add(new Player());
        FaithTrack faithTrack = new FaithTrack(listSection);
        faithTrack.initMarkers(players);
        JsonHandler.saveAsJsonFile("config/temporaryFaithTrack.json", faithTrack);
    }
}
