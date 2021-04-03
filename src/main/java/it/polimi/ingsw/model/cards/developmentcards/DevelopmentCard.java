package it.polimi.ingsw.model.cards.developmentcards;

import it.polimi.ingsw.model.gameresources.Resource;
import it.polimi.ingsw.model.personalboard.StorableResource;

import java.util.ArrayList;

/**
 * this class models the development card with all its attributes like the price, the production power, etc.
 */
public class DevelopmentCard extends GeneralDevelopmentCard{
    private final ArrayList <StorableResource> cost;
    private final ArrayList <StorableResource> consumedResources;
    private final ArrayList <Resource> producedResources;

    /**
     * constructor of the class DevelopmentCard
     * @param cardColour -> superclass' attribute
     * @param cardLevel -> superclass' attribute
     * @param cost -> attribute that indicates che price of the card in terms of the resources that the player has to spend to earn the card
     * @param consumedResources -> attribute that indicates the resources that the player has to spend to star the production power
     * @param producedResources -> attribute that indicates the earned resources after the activation of the production power
     */
    DevelopmentCard(CardColour cardColour, CardLevel cardLevel, ArrayList<StorableResource> cost, ArrayList<StorableResource> consumedResources, ArrayList<Resource> producedResources) {
        super(cardColour, cardLevel);
        this.cost = cost;
        this.consumedResources = consumedResources;
        this.producedResources = producedResources;
    }

    /**
     * getter for the cost attribute
     * @return the created copy of the cost attribute
     */
    private ArrayList <StorableResource> getCost() {
        ArrayList <StorableResource> costCopy = new ArrayList <StorableResource> (this.cost);
        return costCopy;
    }

    /**
     * getter for the consumed resources attribute
     * @return the created copy of the consumed resources attribute
     */
    private ArrayList <StorableResource> getConsumedResources(){
        ArrayList <StorableResource> consumedResourcesCopy = new ArrayList <StorableResource> (this.consumedResources);
        return consumedResourcesCopy;
    }

    /**
     * getter for the produced resources attribute
     * @return the created copy of the produced resources attribute
     */
    private ArrayList <Resource> getProducedResources(){
        ArrayList <Resource> producedResourcesCopy = new ArrayList <Resource> (this.producedResources);
        return producedResourcesCopy;
    }

    /**
     * this method provides a copy of the development card
     * @return the created copy
     */
    DevelopmentCard createCopy() {
        DevelopmentCard copy = new DevelopmentCard(this.getCardColour(), this.getCardLevel(), this.getCost(), this.getConsumedResources(), this.getProducedResources());
        return copy;
    }

    /**
     * this is a method that compares this development card with another one
     * @param cardToCompare -> the card we want to compare
     * @return true if the two cards are equal
     */
    boolean isEqual(DevelopmentCard cardToCompare) {
        return this.getCardColour() == cardToCompare.getCardColour() && this.getCardLevel() == cardToCompare.getCardLevel() && this.cost.equals(cardToCompare.getCost()) && this.consumedResources.equals(cardToCompare.getConsumedResources()) && this.producedResources.equals(cardToCompare.getProducedResources());
    }
}