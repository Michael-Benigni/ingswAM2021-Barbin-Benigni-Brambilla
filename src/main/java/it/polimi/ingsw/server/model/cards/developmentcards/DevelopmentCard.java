package it.polimi.ingsw.server.model.cards.developmentcards;

import it.polimi.ingsw.server.exception.NegativeResourceAmountException;
import it.polimi.ingsw.server.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.server.exception.NullResourceAmountException;
import it.polimi.ingsw.server.model.gamelogic.actions.VictoryPoint;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.stores.StorableResource;

import java.util.ArrayList;
import java.util.Objects;

/**
 * this class models the development card with
 * all its attributes like the price, the production power, etc.
 */
public class DevelopmentCard extends GeneralDevelopmentCard {
    private ArrayList <StorableResource> cost;
    private final ArrayList <StorableResource> consumedResources;
    private final ArrayList <Producible> producedResources;
    private final VictoryPoint victoryPoints;


    /**
     * this method reduces the cost of this development card
     * @param resourceDiscount -> it represents the discount,
     *                         the ResourceType indicates what
     *                         resource of the cost we want to
     *                         reduce and the amount indicates how
     *                         much we want to reduce the cost
     */
    void reduceCost(StorableResource resourceDiscount) {
        for(int i = 0; i < this.cost.size(); i++) {
            try {
                StorableResource newCost = this.cost.get(i).decreaseAmount(resourceDiscount);
                this.cost.set(i, newCost);
            }
            catch (NegativeResourceAmountException | NullResourceAmountException e){
                this.cost.remove(i);
            }
            catch (NotEqualResourceTypeException e) {

            }
        }
    }

    /**
     * constructor of the class DevelopmentCard
     * @param cardColour superclass' attribute
     * @param cardLevel superclass' attribute
     * @param cost attribute that indicates che price of the card in terms of the resources that the player has to spend to earn the card
     * @param consumedResources attribute that indicates the resources that the player has to spend to star the production power
     * @param producedResources attribute that indicates the earned resources after the activation of the production power
     * @param victoryPoints
     */
    public DevelopmentCard(CardColour cardColour, CardLevel cardLevel, ArrayList<StorableResource> cost, ArrayList<StorableResource> consumedResources, ArrayList<Producible> producedResources, VictoryPoint victoryPoints) {
        super(cardColour, cardLevel);
        this.cost = cost;
        this.consumedResources = consumedResources;
        this.producedResources = producedResources;
        this.victoryPoints = victoryPoints;
    }

    /**
     * getter for the cost attribute
     * @return the created copy of the cost attribute
     */
    public ArrayList <StorableResource> getCost() {
        ArrayList <StorableResource> costCopy = new ArrayList <> (this.cost);
        return costCopy;
    }

    /**
     * getter for the consumed resources attribute
     * @return the created copy of the consumed resources attribute
     */
    public ArrayList <StorableResource> getConsumedResources(){
        ArrayList <StorableResource> consumedResourcesCopy = new ArrayList <> (this.consumedResources);
        return consumedResourcesCopy;
    }

    /**
     * getter for the produced resources attribute
     * @return the created copy of the produced resources attribute
     */
    public ArrayList <Producible> getProducedResources(){
        ArrayList <Producible> producedResourcesCopy = new ArrayList <> (0);
        for(int i = 0; i < this.producedResources.size(); i++) {
            producedResourcesCopy.add((Producible) this.producedResources.get(i).clone());
        }
        return producedResourcesCopy;
    }



    /**
     * redefinition of the Object class method equals
     * @param o -> object we want to compare
     * @return true if the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DevelopmentCard)) return false;
        DevelopmentCard card = (DevelopmentCard) o;
        return getCost().equals(card.getCost()) && getConsumedResources().equals(card.getConsumedResources()) && getProducedResources().equals(card.getProducedResources()) && victoryPoints.equals(card.victoryPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCost(), getConsumedResources(), getProducedResources(), victoryPoints);
    }

    @Override
    protected Object clone() {
        DevelopmentCard copy = new DevelopmentCard(this.getCardColour(), this.getCardLevel(), this.getCost(), this.getConsumedResources(), this.getProducedResources(), victoryPoints);
        return copy;
    }

    public VictoryPoint getVictoryPoints() {
        return this.victoryPoints;
    }
}