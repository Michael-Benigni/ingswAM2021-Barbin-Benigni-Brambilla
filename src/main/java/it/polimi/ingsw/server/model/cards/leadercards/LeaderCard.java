package it.polimi.ingsw.server.model.cards.leadercards;


import it.polimi.ingsw.server.model.exception.*;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.faithtrack.FaithPoint;
import it.polimi.ingsw.server.model.gameresources.stores.ResourceType;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;
import java.util.ArrayList;
import java.util.Objects;

/**
 * this class models the leader card.
 * the card that provides a faith point if discarded
 * or one or more effects if activated
 */
public class LeaderCard {
    private final int cardID;
    private boolean isAlreadyPlayed;
    private final ArrayList <Requirement> requirements;
    private final VictoryPoint victoryPoint;
    private transient Effect effect;
    private String effectDesc;

    public int getCardID() {
        return cardID;
    }

    /**
     * this method is the constructor of this class
     * @param cardID
     * @param requirements requirements of this leader card
     * @param victoryPoint amount of victory points of this card
     * @param effect effect that the leader card provides if activated
     */
    public LeaderCard(int cardID, ArrayList<Requirement> requirements, VictoryPoint victoryPoint, Effect effect) {
        this.cardID = cardID;
        this.requirements = requirements;
        this.victoryPoint = victoryPoint;
        this.effect = effect;
        this.effectDesc = null;
        this.isAlreadyPlayed = false;
    }


    /**
     * setter method for the effect
     * @param
     */
    public void setDiscountEffect(StorableResource resource) {
        this.effect = (player, game) -> game.getGameBoard().getDevelopmentCardGrid()
                .addPlayerWithDiscount(player, resource);
        this.effectDesc = "Discount of " + resource;
    }

    public void setExtraDepotEffect (ResourceType resourceType, int depotCapacity){
        this.effect = (player, game) -> player.getPersonalBoard().getWarehouseDepots()
                .addExtraDepot(depotCapacity, resourceType);
        this.effectDesc = "Extra-Depot with capacity\nof  " + depotCapacity + " " + resourceType;
    }

    public void setExtraProductionPowerEffect(StorableResource fixedProducedResource, Producible fixedProducedFP,
                                              int amountToPay, int amountToProduce) {
        this.effect = (player, game) -> player.getPersonalBoard().addExtraProductionPower(fixedProducedResource,
                fixedProducedFP, amountToProduce, amountToPay);
        this.effectDesc = "Extra Production Power" +
                "\nthat consumes " + fixedProducedResource +
                "\nto earn 1 Faith Point and" +
                "\none resource of your choice";
    }

    public void setWhiteMarbleTransformationEffect(StorableResource resource) {
        this.effect = (player, game) ->
                player.getPersonalBoard().getTempContainer().addPlayerModifier(player, resource);
        this.effectDesc = "Transform 1 White Marble\nin " + resource;
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
    public FaithPoint onDiscarded() throws LeaderCardNotDiscardableException {
        if(!this.isAlreadyPlayed)
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
    public void play(Player player, Game game) throws EmptySlotException, NegativeResourceAmountException,
            NotEqualResourceTypeException, NullResourceAmountException, WrongSlotDevelopmentIndexException,
            NoEmptyResourceException, ResourceOverflowInDepotException, LeaderCardNotPlayedException {
        if(!isAlreadyPlayed () && checkRequirementsOf(player)) {
            effect.applyOn (player, game);
            this.isAlreadyPlayed = true;
            SlotLeaderCards auxiliarySlot = player.getPersonalBoard().getSlotLeaderCards();
            auxiliarySlot.notifyUpdate(auxiliarySlot.generateUpdate(auxiliarySlot.getAllNotPlayedCards(),
                    auxiliarySlot.getAllPlayedCards()));
        } else
            throw new LeaderCardNotPlayedException (this.getCardID());
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
    private boolean checkRequirementsOf (Player player) throws NegativeResourceAmountException, EmptySlotException,
            WrongSlotDevelopmentIndexException, NotEqualResourceTypeException, NullResourceAmountException {
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
        return isAlreadyPlayed == card.isAlreadyPlayed && requirements.equals(card.requirements) &&
                victoryPoint.equals(card.victoryPoint) && effect.equals(card.effect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAlreadyPlayed, requirements, victoryPoint, effect);
    }

    boolean isAlreadyPlayed() {
        return isAlreadyPlayed;
    }

    public VictoryPoint getVictoryPoints() {
        return (VictoryPoint) this.victoryPoint.clone();
    }

    public String toString() {
        String toString = "";
        String[] namesSections = {"REQUIREMENTS", "VICTORY POINTS", "EFFECT"};
        for (String name : namesSections) {
            String section = ">> " + name + " <<" + "\n";
            switch (name) {
                case "REQUIREMENTS": {
                    for (Requirement requirement : requirements)
                        section = section + "  " + requirement + "\n";
                    break;
                }
                case "VICTORY POINTS": {
                    section = section + "  " + victoryPoint + "\n";
                    break;
                }
                case "EFFECT": {
                    section = section + "  " + effectDesc + "\n";
                    break;
                }
            }
            toString = toString + "\n" + section ;
        }
        return "This is the Leader Card with ID: " + cardID + "\n" + toString;
    }
}
