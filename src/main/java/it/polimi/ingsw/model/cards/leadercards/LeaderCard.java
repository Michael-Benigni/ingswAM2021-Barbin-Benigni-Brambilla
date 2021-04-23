package it.polimi.ingsw.model.cards.leadercards;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.VictoryPoint;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;

import java.util.ArrayList;

/**
 * interface that represents
 * the effects of the leader card
 */
interface Effect {
    void applyOn(Player p) throws NegativeResourceAmountException;
}

/**
 * this class models the leader card.
 * the card that provides a faith point if discarded
 * or one or more effects if activated
 */
public class LeaderCard {
    private boolean isAlreadyPlayed;
    private ArrayList <Requirement> requirements;
    private VictoryPoint victoryPoint;
    private ArrayList<Effect> effects; //TODO: is it needed to use ArrayList since the Effect is only 1?

    /**
     * this method is the constructor of this class
     * @param requirements -> requirements of this leader card
     * @param victoryPoint -> amount of victory points of this card
     * @param effects -> effects that the leader card provides if activated
     */
    public LeaderCard(ArrayList<Requirement> requirements, VictoryPoint victoryPoint, ArrayList<Effect> effects) {
        this.requirements = requirements;
        this.victoryPoint = victoryPoint;
        this.effects = effects;
        this.isAlreadyPlayed = false;

        Effect discount = (p) -> {gameboard.getCardsGrid.addPlayerWithDiscount(p, tipo di discount)};
        Effect extraDepot = (p) -> {p.getPersonalBoard.getWarehouseDepots.addSpecialDepot(dimensione, tipo di risorsa)};
        //viene aggiunto all'array di depots in warehouse una sottoclasse di depot
        // che avrà una particolare risorsa che puo essere immagazzinata
        Effect transformWhiteMarble = (p) -> {};
        Effect extraProductionPower = (p) -> {p.getPersonalBoard.addExtraProductionPower(consumedResource)};
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
    private void playLeaderCard (Player player) throws EmptySlotException, NegativeResourceAmountException, NotEqualResourceTypeException, NullResourceAmountException, CloneNotSupportedException, WrongSlotDevelopmentIndexException {
        this.isAlreadyPlayed = true;
        if(checkRequirementsOf(player)) {
            for (Effect effect : effects) {
                effect.applyOn(player);
            }
        }
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
