package it.polimi.ingsw.server.model.gameresources.faithtrack;

import it.polimi.ingsw.server.model.exception.CellNotFoundInFaithTrackException;
import it.polimi.ingsw.server.model.exception.GameOverByFaithTrackException;
import it.polimi.ingsw.server.model.exception.NegativeVPAmountException;
import it.polimi.ingsw.server.model.exception.WrongCellIndexException;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        ArrayList<Player> playersSingleGame = new ArrayList<> (players);
        createBlackCross();
        playersSingleGame.add(blackCross);
        super.initMarkers(playersSingleGame);
        notifyBlackCrossCreation();
    }

    private void notifyBlackCrossCreation() {
        ArrayList<Player> players = new ArrayList<> (getMapOfFaithMarkers ().keySet ());
        ArrayList<Observer> observers = players.get (0).getObservers ();
        blackCross.attachAll (observers);
        blackCross.notifyUpdate (blackCross.getPositionUpdate ());
    }

    private void createBlackCross() {
        blackCross = new Player();
        blackCross.setUsername ("BLACK CROSS");
        blackCross.setPosition (-1);
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
