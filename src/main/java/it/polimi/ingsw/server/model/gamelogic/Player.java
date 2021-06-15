package it.polimi.ingsw.server.model.gamelogic;

import it.polimi.ingsw.server.model.GameComponent;
import it.polimi.ingsw.server.model.cards.developmentcards.SlotDevelopmentCards;
import it.polimi.ingsw.server.model.exception.EmptySlotException;
import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.exception.WrongSlotDevelopmentIndexException;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCard;
import it.polimi.ingsw.server.model.gamelogic.actions.PersonalBoard;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayList;

/**
 * Class that represents the player in the model.
 * and a number of victory points.
 * They start to zero and can be increased during the match.
 */
public class Player implements GameComponent {

    private VictoryPoint victoryPoints;
    private PersonalBoard personalBoard;
    private int position;
    private boolean isConnected;
    private String username;
    private Observer observer;


    /**
     * Constructor method of this class.
     */
    public Player() {
        super();
        this.victoryPoints = new VictoryPoint(0);
        isConnected = true;
    }


    /**
     * @param position set the position of the player in the rounds of the game
     */
    public void setPosition(int position) {
        this.position = position;
        notifyUpdate (positionInTurnUpdate());
    }


    /**
     * @return a Sendable object that contains the information of the Player position in game turn
     */
    private Sendable positionInTurnUpdate() {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.TURN_POSITION_UPDATE);
        writer.addProperty ("turnPosition", this.position);
        return writer.write ();
    }


    /**
     * @param personalBoard the PersonalBoard that is set into this Player
     */
    public void buildBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
        this.personalBoard.getStrongbox ().attach(observer);
        for (SlotDevelopmentCards slot : this.personalBoard.getListOfSlotDevelopmentCards ())
            slot.attach (observer);
        this.personalBoard.getSlotLeaderCards ().attach(observer);
        this.personalBoard.getTempContainer ().attach(observer);
        this.personalBoard.getWarehouseDepots ().attach (observer);
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
     */
    public ArrayList <DevelopmentCard> getDevCardRequirements() {
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
     * @param card the card to buy
     * @return true if the Player has the resources required, else it returns false
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

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setIsConnected(boolean connected) {
        isConnected = connected;
    }

    @Override
    public ArrayList<Observer> getObservers() {
        ArrayList<Observer> observers = new ArrayList<> ();
        if (observer != null)
            observers.add (observer);
        return observers;
    }

    /**
     * This method is used to attach the observer to the object that implements this interface
     *
     * @param observer
     */
    @Override
    public void attach(Observer observer) {
        this.observer = observer;
    }

    public Sendable getPositionUpdate() {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.PLAYER_POSITION_UP);
        writer.addProperty ("name", username);
        writer.addProperty ("positionInTurn", position);
        return writer.write ();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPosition() {
        return position;
    }

    public String getUsername() {
        return username;
    }
}
