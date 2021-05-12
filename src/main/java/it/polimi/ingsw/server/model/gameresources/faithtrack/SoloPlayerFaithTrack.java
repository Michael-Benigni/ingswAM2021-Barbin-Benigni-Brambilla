package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.exception.GameOverByFaithTrackException;
import it.polimi.ingsw.server.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.exception.WrongCellIndexException;
import it.polimi.ingsw.server.model.gamelogic.Player;

import java.util.ArrayList;

public class SoloPlayerFaithTrack extends FaithTrack {

    private Player comPlayer;

    /**
     * Constructor method of this class. It builds the entire structure of the track.
     *
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
}