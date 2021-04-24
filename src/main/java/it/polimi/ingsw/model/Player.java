package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.EmptySlotException;
import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents the player in the model.
 * and a number of victory points.
 * They start to zero and can be increased during the match.
 */
public class Player {

    private VictoryPoint victoryPoints;
    private PersonalBoard personalBoard;


    /**
     * Constructor method of this class.
     */
    public Player() {
        this.victoryPoints = new VictoryPoint(0);
        this.personalBoard = new PersonalBoard(0, 0);
    }


    /**
     * Method that increases the number of victory points of this player.
     * @param victoryPoint -> victory points to be added to the victory points already contained in this player.
     */
    public void addVictoryPointsToPlayer(VictoryPoint victoryPoint){
        this.victoryPoints.increaseVictoryPoints(victoryPoint);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return victoryPoints.equals(player.victoryPoints) && personalBoard.equals(player.personalBoard);
    }


    @Override
    public int hashCode() {
        return Objects.hash(victoryPoints, personalBoard);
    }


    /**
     * @return the PersonalBoard of the player
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
