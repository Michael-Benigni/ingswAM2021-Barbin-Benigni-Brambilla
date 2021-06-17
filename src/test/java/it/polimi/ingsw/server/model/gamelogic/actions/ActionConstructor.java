package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.model.gamelogic.MultiplayerGame;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to create an array that contains the actions you want to use outside the "actions" package.
 */
public class ActionConstructor {

    public static HashMap<String, Action> getPossibleAction() throws NegativeResourceAmountException {
        HashMap<String, Action> actions = new HashMap<>();
        actions.put("END_TURN", new MultiplayerGame.EndTurnMultiplayerAction ());
        actions.put("ALWAYS_VALID", new StrongboxAction(PayAction.StoreOrRemove.STORE, new StorableResource(ResourceType.COIN, 5)));
        actions.put("UNIQUE", new MarketAction(0,"row"));
        actions.put("START_PRODUCTION", new StartProductionAction());
        ArrayList<PayAction> listOfPayAction = new ArrayList<>();
        listOfPayAction.add(new StrongboxAction(PayAction.StoreOrRemove.STORE, new StorableResource(ResourceType.STONE, 1)));
        listOfPayAction.add(new StrongboxAction(PayAction.StoreOrRemove.STORE, new StorableResource(ResourceType.SHIELD, 1)));
        actions.put("PRODUCTION", new BoardProductionAction(new StorableResource(ResourceType.COIN, 1), listOfPayAction));
        actions.put("END_PRODUCTION", new EndProductionAction());
        return actions;
    }
}
