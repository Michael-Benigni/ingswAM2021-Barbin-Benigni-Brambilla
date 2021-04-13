package it.polimi.ingsw.model;

import it.polimi.ingsw.model.config.ConfigLoaderWriter;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithTrack;
import it.polimi.ingsw.model.gameresources.faithtrack.Section;

import java.util.ArrayList;

/**
 * Class that represents the game board, that's a collection of elements that are common to all players.
 */
public class GameBoard {

    FaithTrack faithTrack;
    //DevelopmentCardGrid developmentCardGrid;

    /**
     * Constructor method of this class
     */
    public GameBoard() {

    }

    /**
     * Method that initializes the elements of this game board.
     * @param listOfPlayers -> array that contains all the players.
     */
    public void prepareGameBoard(ArrayList<Player> listOfPlayers) throws Exception {
        //this.developmentCardGrid = new DevelopmentCardGrid();
        //TODO: read from json file the array of section to pass in the constructor method of "FaithTrack" class.
        int numSections = (int) ConfigLoaderWriter.getAsJavaObjectFromJSON(int.class, "FaithTrack/numberOfSections");
        ArrayList<Section> listOfSections = new ArrayList<>(0);
        for(int i = 0; i < numSections; i++){
            //listOfSections.add(getSectionFromJSON(i));
        }
        this.faithTrack = new FaithTrack(listOfSections, listOfPlayers);
    }

    /*
    private Section getSectionFromJSON(int sectionCounter){
        ArrayList<Cell> listOfCell = new ArrayList<>(0);
        return ;
    }*/


    /**
     * Getter method for "faithTrack" attribute of this class.
     * @return -> the faith track.
     */
    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

}
