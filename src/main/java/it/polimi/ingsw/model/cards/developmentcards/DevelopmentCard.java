package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.exception.NotEqualResourceTypeException;
import it.polimi.ingsw.exception.NullResourceAmountException;
import it.polimi.ingsw.model.VictoryPoint;
import it.polimi.ingsw.model.cards.leadercards.Options;
import it.polimi.ingsw.model.gameresources.markettray.Resource;
import it.polimi.ingsw.model.gameresources.stores.StorableResource;
import java.util.ArrayList;

//TODO: la action che fa la produzione chiamerà il metodo check consumed resources che appartiene alla dev card e qui verrà chiamata la contained in sulle consumed resources. e ritorno le risorse consumate (copia) cosi la action le potrà togliere dal deposito

/**
 * this class models the development card with
 * all its attributes like the price, the production power, etc.
 */
public class DevelopmentCard extends GeneralDevelopmentCard implements Options {
    private final ArrayList <StorableResource> cost;
    private final ArrayList <StorableResource> consumedResources;
    private final ArrayList <Resource> producedResources;
    private final VictoryPoint victoryPoints;

    //TODO:check consumed resources 
    void checkConsumedResources() {

    }

    /**
     * this method reduces the cost of this development card
     * @param resourceDiscount -> it represents the discount,
     *                         the ResourceType indicates what
     *                         resource of the cost we want to
     *                         reduce and the amount indicates how
     *                         much we want to reduce the cost
     */
    void reduceCost(ArrayList <StorableResource> resourceDiscount) {
        for(int i = 0; i < this.cost.size(); i++) {
            try {
                this.cost.get(i).decreaseAmount(resourceDiscount.get(i));
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
     * @param cardColour -> superclass' attribute
     * @param cardLevel -> superclass' attribute
     * @param cost -> attribute that indicates che price of the card in terms of the resources that the player has to spend to earn the card
     * @param consumedResources -> attribute that indicates the resources that the player has to spend to star the production power
     * @param producedResources -> attribute that indicates the earned resources after the activation of the production power
     * @param victoryPoints
     */
    public DevelopmentCard(CardColour cardColour, CardLevel cardLevel, ArrayList<StorableResource> cost, ArrayList<StorableResource> consumedResources, ArrayList<Resource> producedResources, VictoryPoint victoryPoints) {
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
    private ArrayList <StorableResource> getCost() {
        ArrayList <StorableResource> costCopy = new ArrayList <> (this.cost);
        return costCopy;
    }

    /**
     * getter for the consumed resources attribute
     * @return the created copy of the consumed resources attribute
     */
    private ArrayList <StorableResource> getConsumedResources(){
        ArrayList <StorableResource> consumedResourcesCopy = new ArrayList <> (this.consumedResources);
        return consumedResourcesCopy;
    }

    /**
     * getter for the produced resources attribute
     * @return the created copy of the produced resources attribute
     */
    private ArrayList <Resource> getProducedResources(){
        ArrayList <Resource> producedResourcesCopy = new ArrayList <> (0);
        for(int i = 0; i < this.producedResources.size(); i++) {
            producedResourcesCopy.add((Resource) this.producedResources.get(i).clone());
        }
        return producedResourcesCopy;
    }

    /**
     * redefinition of the Object class method equals
     * @param card -> card we want to compare
     * @return true if the two cards are equal
     */
    @Override
    public boolean equals(Object card) {
        if(this.getCardLevel().compareTo(((DevelopmentCard)card).getCardLevel()) == 0 && this.getCardColour().compareTo(((DevelopmentCard)card).getCardColour()) == 0 && this.cost.equals(((DevelopmentCard) card).cost) && this.consumedResources.equals(((DevelopmentCard) card).consumedResources) && this.producedResources.equals(((DevelopmentCard) card).producedResources)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * redefinition of the Object class method clone
     * @return an object that is che created copy of the caller object
     */
    @Override
    protected Object clone() {
        DevelopmentCard copy = new DevelopmentCard(this.getCardColour(), this.getCardLevel(), this.getCost(), this.getConsumedResources(), this.getProducedResources(), victoryPoints);
        return copy;
    }
}