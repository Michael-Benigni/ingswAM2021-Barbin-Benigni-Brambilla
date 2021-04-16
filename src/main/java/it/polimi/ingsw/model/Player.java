package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.model.cards.leadercards.Requirement;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Class that represents the player in the model. It has an username provided by the client, and a number of victory
 * points. They start to zero and can be increased during the match.
 */
public class Player {

    private String username;
    private VictoryPoint victoryPoints;
    private PersonalBoard personalBoard;

    /**
     * Constructor method of this class.
     * @param username -> String provided by the client.
     * @throws NegativeVPAmountException -> can be thrown by constructor method of "VictoryPoint" class.
     */
    Player(String username) throws FileNotFoundException {
        this.username = username;
        try {
            this.victoryPoints = new VictoryPoint(0);
        } catch (NegativeVPAmountException e) {
            this.victoryPoints = null;
        }
        this.personalBoard = new PersonalBoard(0, 0).initFromJSON();
    }

    /**
     * Method that increases the number of victory points of this player.
     * @param victoryPoint -> victory points to be added to the victory points already contained in this player.
     */
    public void addVictoryPointsToPlayer(VictoryPoint victoryPoint){
        this.victoryPoints.increaseVictoryPoints(victoryPoint);
    }

    /**
     * Method that overrides the "equals" method of "Object" class.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    ArrayList<Requirement> getAllRequirements() {
        ArrayList<Requirement> requirements = new ArrayList<>(0);
        // getAll from Strongbox, warehouse, slots, ... and put in array;
        return requirements;
    }
}
