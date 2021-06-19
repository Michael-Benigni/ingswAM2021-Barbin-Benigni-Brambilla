package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.exception.GameOverByFaithTrackException;
import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.exception.WrongCellIndexException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.utils.Observer;

import java.util.ArrayList;

public class SoloPlayerFaithTrack extends FaithTrack {

    private Player blackCross;

    /**
     * Constructor method of this class. It builds the entire structure of the track.
     *
     * @param arrayOfSections
     */
    public SoloPlayerFaithTrack(ArrayList<Section> arrayOfSections) {
        super (arrayOfSections);
    }


    @Override
    public void initMarkers(ArrayList<Player> players) {
        Player singlePlayer = players.get (0);
        ArrayList<Player> playersSingleGame = new ArrayList<> (players);
        createBlackCross();
        playersSingleGame.add(blackCross);
        super.initMarkers(playersSingleGame);
        notifyBlackCrossCreation(singlePlayer.getObservers ());
    }

    private void notifyBlackCrossCreation(ArrayList<Observer> observers) {
        blackCross.attachAll (observers);
        blackCross.notifyUpdate (blackCross.getPlayerInfoForOtherUpdate ());
    }

    private void createBlackCross() {
        blackCross = new Player();
        blackCross.setUsername ("BLACK CROSS");
        blackCross.setPosition (2);
    }


    /**
     * Method that move the black cross forward by a provided number of cells.
     * @param numberOfSteps integer representing how many cells the black cross must move.
     */
    public void moveBlackCross(int numberOfSteps) throws WrongCellIndexException, CellNotFoundInFaithTrackException, GameOverByFaithTrackException, NegativeVPAmountException {
        super.moveMarkerForward(blackCross, numberOfSteps);
    }

    /**
     * Getter method for the current cell of the black cross indicator.
     * @return the current position of the black cross.
     */
    public Cell getBlackCrossPosition() {
        return this.getMapOfFaithMarkers().get(blackCross).getCurrentCell();
    }
}
