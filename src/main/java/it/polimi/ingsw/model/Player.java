package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.NegativeVPAmountException;

/**
 * Class that represents the player in the model. It has an username provided by the client, and a number of victory
 * points. They start to zero and can be increased during the match.
 */
public class Player {

    private String username;
    private VictoryPoint victoryPoints;
    private GameBoard gameBoard;
    private PersonalBoard personalBoard;

    /**
     * Constructor method of this class.
     * @param username -> String provided by the client.
     * @throws NegativeVPAmountException -> can be thrown by constructor method of "VictoryPoint" class.
     */
    Player(String username, GameBoard gameBoard) throws NegativeVPAmountException {
        this.username = username;
        this.victoryPoints = new VictoryPoint(0);
        this.gameBoard = gameBoard;
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
     * Getter method for "gameBoard" attribute of this class.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void performAction(Action action) {
        action.executeOn(this);
    }
}
