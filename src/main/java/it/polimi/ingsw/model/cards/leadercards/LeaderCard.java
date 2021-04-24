package it.polimi.ingsw.model.cards.leadercards;

import com.google.gson.annotations.Expose;
import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VictoryPoint;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * this class models the leader card.
 * the card that provides a faith point if discarded
 * or one or more effects if activated
 */
public class LeaderCard {
    private boolean isAlreadyPlayed;
    private ArrayList <Requirement> requirements;
    private VictoryPoint victoryPoint;
    private transient Effect effect; //TODO: is it needed to use ArrayList since the Effect is only 1?

    /**
     * this method is the constructor of this class
     * @param requirements -> requirements of this leader card
     * @param victoryPoint -> amount of victory points of this card
     * @param effect -> effects that the leader card provides if activated
     */
    public LeaderCard(ArrayList<Requirement> requirements, VictoryPoint victoryPoint, Effect effect) {
        this.requirements = requirements;
        this.victoryPoint = victoryPoint;
        this.effect = effect;
        this.isAlreadyPlayed = false;
    }

    public final void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void setEffectFromJSON(String jsonPath, int[] ints) throws FileNotFoundException {
        jsonPath = jsonPath + "effect/";
        String typeEffect = (String) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(String.class, jsonPath + "effectType", ints);
        switch (typeEffect) {
            case "discount": {
                StorableResource resource = (StorableResource) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
                this.effect = (player, game) -> game.getGameBoard().getDevelopmentCardGrid();//.addPlayerWithDiscount(player, resource);
                break;
            }
            case "extraDepot": {
                StorableResource resource = (StorableResource) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
                int depotCapacity = (int) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(int.class, jsonPath + "capacity", ints);
                this.effect = (player, game) -> player.getPersonalBoard();//.addExtraDepot(depotCapacity, resource);
                break;
            }
            case "extraProductionPower": {
                StorableResource resource = (StorableResource) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
                this.effect = (player, game) -> player.getPersonalBoard();//.addExtraProductionPower(resource);
                break;
            }
            case "transformWhiteMarble": {
                StorableResource resource = (StorableResource) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
                this.effect = (player, game) -> {};
                break;
            }
            default: {
                //TODO:
            }
        }
    }

    /**
     * this method represents the action
     * made by the player that allow him
     * to discard a not activated leader
     * card to obtain one faith point.
     * if this method works and it returns one faith point,
     * the caller must invoke the method that removes the
     * leader card from the slot leader card of the player
     * @return -> the faith point that the player earns
     */
    private FaithPoint discardLeaderCard() {
        try {
            if(this.isAlreadyPlayed == false)
                return new FaithPoint(1);
            else
                return null;
        } catch (NegativeResourceAmountException e) {
            return null;
        }
    }

    /**
     * this method invokes the checkRequirementsOf
     * and if this method returns true it activates
     * the effects of the leader card
     * @param player -> this is the reference to the player
     * @throws EmptySlotException
     * @throws NegativeResourceAmountException
     * @throws NotEqualResourceTypeException
     * @throws NullResourceAmountException
     * @throws CloneNotSupportedException
     * @throws WrongSlotDevelopmentIndexException
     */
    private void playLeaderCard (Player player, Game game) throws EmptySlotException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, CloneNotSupportedException, WrongSlotDevelopmentIndexException {
        this.isAlreadyPlayed = true;
        if(checkRequirementsOf(player))
                effect.applyOn(player, game);
    }

    /**
     * this method checks if the player
     * can activate the leader card.
     * if the player satisfies all the
     * requirements of the leader card,
     * he can plays the card
     * @param player -> this is the reference to the player
     * @return -> boolean: true = player satisfies requirements
     *                    false = player doesn't satisfy requirements
     * @throws NegativeResourceAmountException
     * @throws CloneNotSupportedException
     * @throws EmptySlotException
     * @throws WrongSlotDevelopmentIndexException
     * @throws NotEqualResourceTypeException
     * @throws NullResourceAmountException
     */
    private boolean checkRequirementsOf (Player player) throws NegativeResourceAmountException, CloneNotSupportedException, EmptySlotException, WrongSlotDevelopmentIndexException, NotEqualResourceTypeException, NullResourceAmountException {
        for(int i = 0; i < this.requirements.size(); i++) {
            if(! this.requirements.get(i).containedIn(player))
                return false;
        }
        return true;
    }
}
