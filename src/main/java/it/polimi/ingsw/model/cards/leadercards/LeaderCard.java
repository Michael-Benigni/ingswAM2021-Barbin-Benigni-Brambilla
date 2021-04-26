package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.config.ConfigLoaderWriter;
import it.polimi.ingsw.model.gamelogic.Game;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import it.polimi.ingsw.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * this class models the leader card.
 * the card that provides a faith point if discarded
 * or one or more effects if activated
 */
public class LeaderCard {
    private boolean isAlreadyPlayed;
    private ArrayList <Requirement> requirements;
    private VictoryPoint victoryPoint;
    private transient Effect effect;

    /**
     * this method is the constructor of this class
     * @param requirements requirements of this leader card
     * @param victoryPoint amount of victory points of this card
     * @param effect effect that the leader card provides if activated
     */
    public LeaderCard(ArrayList<Requirement> requirements, VictoryPoint victoryPoint, Effect effect) {
        this.requirements = requirements;
        this.victoryPoint = victoryPoint;
        this.effect = effect;
        this.isAlreadyPlayed = false;
    }

    /**
     * setter method for the effect
     * @param effect is the effect of the leader card, if the card is played the effect is activated
     */
    public final void setEffect(Effect effect) {
        this.effect = effect;
    }

    /**
     * this method is used to set the effect of the leader card from the database
     * @param jsonPath is the path of the file that represents the database
     * @param ints is an index used to pick the information from the json file
     * @throws FileNotFoundException
     */
    public void setEffectFromJSON(String jsonPath, int[] ints) throws FileNotFoundException {
        jsonPath = jsonPath + "effect/";
        String typeEffect = (String) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(String.class, jsonPath + "effectType", ints);
        switch (typeEffect) {
            case "discount": {
                StorableResource resource = (StorableResource) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
                this.effect = (player, game) -> game.getGameBoard().getDevelopmentCardGrid().addPlayerWithDiscount(player, resource);
                break;
            }
            case "extraDepot": {
                StorableResource resource = (StorableResource) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
                int depotCapacity = (int) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(int.class, jsonPath + "capacity", ints);
                this.effect = (player, game) -> player.getPersonalBoard().getWarehouseDepots().addExtraDepot(depotCapacity, resource);
                break;
            }
            case "extraProductionPower": {
                StorableResource resource = (StorableResource) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
                this.effect = (player, game) -> player.getPersonalBoard().addExtraProductionPower(resource);
                break;
            }
            case "transformWhiteMarble": {
                StorableResource resource = (StorableResource) ConfigLoaderWriter.getAsJavaObjectFromJSONArray(StorableResource.class, jsonPath + "resource", ints);
                this.effect = (player, game) -> {
                    try {
                        player.getPersonalBoard().getTempContainer().addPlayerModifier(player, resource);
                    } catch (AlreadyAddedModifier alreadyAddedModifier) {
                        player.getPersonalBoard().getTempContainer().transformEmptyResources(player, resource);
                    }
                };
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
     * @return the faith point that the player earns
     */
    public FaithPoint onDiscarded() throws LeaderCardNotDiscardableException{
        if(this.isAlreadyPlayed == false)
            return new FaithPoint(1);
        else
            throw new LeaderCardNotDiscardableException();
    }

    /**
     * this method invokes the checkRequirementsOf
     * and if this method returns true it activates
     * the effects of the leader card
     * @param player this is the reference to the player
     * @param game this is the refer to the game
     * @throws EmptySlotException
     * @throws NegativeResourceAmountException
     * @throws NotEqualResourceTypeException
     * @throws NullResourceAmountException
     * @throws WrongSlotDevelopmentIndexException
     */
    public void play(Player player, Game game) throws EmptySlotException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException, NoEmptyResourceException {
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
     * @param player this is the reference to the player
     * @return boolean value: true = player satisfies requirements
     *                    false = player doesn't satisfy requirements
     * @throws NegativeResourceAmountException
     * @throws EmptySlotException
     * @throws WrongSlotDevelopmentIndexException
     * @throws NotEqualResourceTypeException
     * @throws NullResourceAmountException
     */
    private boolean checkRequirementsOf (Player player) throws NegativeResourceAmountException, EmptySlotException, WrongSlotDevelopmentIndexException, NotEqualResourceTypeException, NullResourceAmountException {
        for(int i = 0; i < this.requirements.size(); i++) {
            if(! this.requirements.get(i).containedIn(player))
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaderCard)) return false;
        LeaderCard card = (LeaderCard) o;
        return isAlreadyPlayed == card.isAlreadyPlayed && requirements.equals(card.requirements) && victoryPoint.equals(card.victoryPoint) && effect.equals(card.effect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAlreadyPlayed, requirements, victoryPoint, effect);
    }
}
