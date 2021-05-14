package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.exception.GameOverByFaithTrackException;
import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.exception.WrongCellIndexException;
import it.polimi.ingsw.server.model.gamelogic.Player;

import java.util.ArrayList;

public class SoloPlayerFaithTrack extends FaithTrack {

    private Player comPlayer;

    /**
     * Constructor method of this class. It builds the entire structure of the track.
     * @param arrayOfSections
     */
    public SoloPlayerFaithTrack(ArrayList<Section> arrayOfSections) {
        super(arrayOfSections);
        comPlayer = new Player();
    }

    @Override
    public void initMarkers(ArrayList<Player> players) {
        players.add(comPlayer);
        super.initMarkers(players);
    }

    /**
     * Method that move the black cross forward by a provided number of cells.
     * @param numberOfSteps integer representing how many cells the black cross must move.
     */
    public void moveBlackCross(int numberOfSteps) throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException, NegativeVPAmountException {
        super.moveMarkerForward(comPlayer, numberOfSteps);
    }

    /**
     * Getter method for the current cell of the black cross indicator.
     * @return the current position of the black cross.
     */
    public Cell getBlackCrossPosition() {
        return this.getMapOfFaithMarkers().get(comPlayer).getCurrentCell();
    }
}
