package it.polimi.ingsw.model.actions;

import it.polimi.ingsw.exception.WrongCommandException;
import it.polimi.ingsw.model.gamelogic.Action;
import java.util.HashMap;

/**
 * This is the class that builds the action correspondent to a certain command with certain options
 */
public class ActionFactory {

    /**
     * This is the factory method that builds the action according to the input parameters
     * @param command -> it's the String that indicates which action should be buildt
     * @param options -> HashMap that has different options according to the command: this HashMap is used to get the
     *                parameters to build the Action indicated by the command, and for each Action the options will be
     *                different
     * @return the Action correspondent to the command and buildt according to the options
     * @throws WrongCommandException if the command does not represent any Action
     */
    public Action getAction(String command, HashMap<String, String> options) throws WrongCommandException {
        switch (command) {
            case "START_TURN":
                return new StartTurnAction();
            case "MARKET_ROW":
                int row = Integer.parseInt(options.get("row"));
                return null;
            case "MARKET_COLUMN":
                return null;
            case "BUY_CARD":
                return null;
            case "START_PRODUCTION":
                return null;
            case "SWAP_DEPOTS":
                return null;
            case "STORE_RESOURCES":
                return null;
            case "LEADER_PLAY":
                return null;
            case "LEADER_DISCARD":
                return null;
            case "END_TURN":
                return null;
            default:
                throw new WrongCommandException();
        }
    }
}
