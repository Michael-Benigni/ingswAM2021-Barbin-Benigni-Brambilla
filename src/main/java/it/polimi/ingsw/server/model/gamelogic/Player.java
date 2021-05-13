package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.exception.EmptySlotException;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

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
    private int position;


    /**
     * @param position set the position of the player in the rounds of the game
     */
    public void setPosition(int position) {
        this.position = position;
    }


    /**
     * Constructor method of this class.
     */
    public Player() {
        this.victoryPoints = new VictoryPoint(0);
    }


    public void buildBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
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
     * @throws EmptySlotException
     * @throws WrongSlotDevelopmentIndexException
     */
    public ArrayList <StorableResource> getResourceRequirements() {
        ArrayList <StorableResource> resourceRequirements;
        resourceRequirements = this.personalBoard.getAllResource();
        return resourceRequirements;
    }


    /**
     * this method invokes the personal board
     * method getDevCardRequirements to obtain an
     * ArrayList of the player's requirements
     * represented by colour and level of development cards
     * @return an ArrayList of Requirements (DevelopmentCard)
     * @throws NegativeResourceAmountException
     * @throws EmptySlotException
     * @throws WrongSlotDevelopmentIndexException
     */
    public ArrayList <DevelopmentCard> getDevCardRequirements() throws EmptySlotException, WrongSlotDevelopmentIndexException {
        ArrayList <DevelopmentCard> devCardRequirements;
        devCardRequirements = this.personalBoard.getAllDevelopmentCards();
        return devCardRequirements;
    }


    /**
     * @return the PersonalBoard of the player
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }


    /**
     *
     */
    public boolean canBuy (DevelopmentCard card) {
        boolean result = false;
        ArrayList<StorableResource> cost = card.getCost();
        for (StorableResource resource : cost)
            result = resource.containedIn(this);
        return result;
    }


    /**
     * Getter method for "victoryPoints" attribute of this class.
     * @return -> a copy of the stored victory point.
     */
    VictoryPoint getVictoryPoints() {
        return (VictoryPoint) victoryPoints.clone();
    }


    public VictoryPoint computeAllVP() {
        VictoryPoint copyVictoryPoints = (VictoryPoint) this.victoryPoints.clone();
        copyVictoryPoints.increaseVictoryPoints(this.personalBoard.computeTotalVP());
        return copyVictoryPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(getVictoryPoints(), player.getVictoryPoints()) && Objects.equals(getPersonalBoard(), player.getPersonalBoard()) && position == player.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(victoryPoints, personalBoard);
    }
}
