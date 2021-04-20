package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NegativeVPAmountException;
import it.polimi.ingsw.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Class that represents the player in the model.
 * It has a username provided by the client,
 * and a number of victory points.
 * They start to zero and can be increased during the match.
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

    /**
     * this method invokes the personal board
     * method getResourceRequirements to obtain an
     * ArrayList of the player's requirements
     * represented by storable resources
     * @return an ArrayList of Requirements (StorableResource)
     * @throws NegativeResourceAmountException
     * @throws CloneNotSupportedException
     * @throws EmptySlotException
     * @throws WrongSlotDevelopmentIndexException
     */
    public ArrayList <StorableResource> getResourceRequirements() throws CloneNotSupportedException {
        ArrayList <StorableResource> resourceRequirements;
        resourceRequirements = this.personalBoard.getResourceRequirements();
        return resourceRequirements;
    }

    /**
     * this method invokes the personal board
     * method getDevCardRequirements to obtain an
     * ArrayList of the player's requirements
     * represented by colour and level of development cards
     * @return an ArrayList of Requirements (DevelopmentCard)
     * @throws NegativeResourceAmountException
     * @throws CloneNotSupportedException
     * @throws EmptySlotException
     * @throws WrongSlotDevelopmentIndexException
     */
    public ArrayList <DevelopmentCard> getDevCardRequirements() throws EmptySlotException, WrongSlotDevelopmentIndexException {
        ArrayList <DevelopmentCard> devCardRequirements;
        devCardRequirements = this.personalBoard.getDevCardRequirements();
        return devCardRequirements;
    }
}
