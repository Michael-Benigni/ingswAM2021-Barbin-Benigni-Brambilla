package it.polimi.ingsw.model.gameresources.faithtrack;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VictoryPoint;

import java.util.ArrayList;

/**
 * Class that represents the common faith track on the game board. Every player has a marker that indicates its position on the track.
 */
public class FaithTrack {

    private ArrayList<Section> listOfSection;
    private ArrayList<FaithMarker> listOfFaithMarker;


    /**
     * Constructor method of this class. It reads from a json file the entire structure of the track.
     */
    public FaithTrack(ArrayList<Section> arrayOfSections, ArrayList<Player> listOfPlayers) throws WrongCellIndexException, NegativeVPAmountException {
        this.listOfSection = new ArrayList<>(0);
        this.listOfSection = arrayOfSections;
        this.listOfFaithMarker = new ArrayList<>(0);
        Cell firstCell = firstCellInFaithTrack();
        for(Player p : listOfPlayers) {
            FaithMarker temporaryFaithMarker = new FaithMarker(p, firstCell);
            this.listOfFaithMarker.add(temporaryFaithMarker);
        }
    }


    /**
     * Method that returns the first cell of the track to the caller.
     *
     * @return -> the first cell of the track.
     */
    private Cell firstCellInFaithTrack() throws WrongCellIndexException {
        return listOfSection.get(0).getCell(0);
    }


    /**
     * Method that update the provided cell with the next on the track.
     * @param currentCell -> the current cell, so the caller wants the next one.
     * @return -> the next cell.
     */
    Cell nextCell(Cell currentCell) throws CellNotFoundInFaithTrackException {
        for (Section s : listOfSection) {
            try {
                Cell nextCell = s.searchNextCellInSection(currentCell);
                return nextCell;
            } catch (CellNotFoundInSectionException e) {
                //do nothing and go to the next section in the arraylist.
            } catch (LastCellInSectionException e) {
                int sectionIndex = listOfSection.indexOf(s) + 1;
                return listOfSection.get(sectionIndex).firstCellInSection();
            }
        }
        throw new CellNotFoundInFaithTrackException();
    }


    /**
     * Method that return the section that contains the provided cell.
     * @param currentCell -> cell to find in the faith track.
     * @return -> the section if exists.
     * @throws CellNotFoundInFaithTrackException -> exception thrown if the provided cell doesn't exist in this
     * faith track.
     */
    private Section findSectionOfThisCell(Cell currentCell) throws CellNotFoundInFaithTrackException {
        for(Section s : listOfSection) {
            try {
                s.searchInThisSection(currentCell);
                return s;
            } catch (CellNotFoundInSectionException e) {
                //do nothing and go to the next section in the arraylist.
            }
        }
        throw new CellNotFoundInFaithTrackException();
    }


    /**
     * Method that move the marker that belongs to the provided player by a provide number of cells.
     * @param player
     * @param numberOfSteps -> number of cells.
     * @throws Exception
     */
    void moveMarkerForward(Player player, int numberOfSteps) throws Exception {
        FaithMarker faithMarker = getMarkerOfPlayer(player);
        for(int i = 0; i < numberOfSteps; i++) {
            faithMarker.moveForward(this);
        }
    }


    /**
     * Method that return the faith marker that belongs to the provided player.
     * @param player
     * @return -> the faith marker if exists.
     * @throws PlayerHasNotMarkerException -> exception thrown if the provided player hasn't a faith marker.
     */
    private  FaithMarker getMarkerOfPlayer(Player player) throws PlayerHasNotMarkerException {
        for(FaithMarker f : listOfFaithMarker) {
            if(f.belongsTo(player)) {
                return f;
            }
        }
        throw new PlayerHasNotMarkerException();
    }


    /**
     * Method that handles the activation of a pope space. It adds victory points to the current player and check the
     * positions of the other players: if they share the current section with the current player, those players gain victory points too.
     * @param popeCell -> current cell (it will always be a pope space one).
     * @param currentPlayer -> current player, who activated the pope space.
     * @throws Exception
     */
    void activatePopeSpaceEffect(Cell popeCell, Player currentPlayer) throws Exception {
        //activate tile of the provided player.
        VaticanReportSection currentSection = (VaticanReportSection) findSectionOfThisCell(popeCell);
        PopeFavourTile currentTile = currentSection.getTile();
        try{
            VictoryPoint victoryPoint = currentTile.activateTile();
            currentPlayer.addVictoryPointsToPlayer(victoryPoint);
            //if it wasn't already activated, check the other players.
            for(FaithMarker f : listOfFaithMarker) {
                if(!(f.belongsTo(currentPlayer)) && (f.ifIsInThisSection(currentSection))) {
                    Player player = f.getPlayer();
                    player.addVictoryPointsToPlayer(victoryPoint);
                }
            }
        } catch (TileAlreadyActivatedException e){
            //do nothing, because the tile was already activated.
        }
    }
}
