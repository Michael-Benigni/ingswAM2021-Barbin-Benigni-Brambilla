package it.polimi.ingsw.client.view.lightweightmodel;

/**
 * Class that represents the view of a "Extra Production" power.
 */
public class LWXProductionPower {

    /**
     * Resource consumed by this power.
     */
    private final LWResource consumedResource;

    /**
     * Amount of the consumed resource
     */
    private final Integer numberOfResourceToPay;

    /**
     * Amount of the produced resource
     */
    private final Integer numberOfResourceToProduce;

    /**
     * Resource produced by this power.
     */
    private final LWProducible produced;

    /**
     * Index of this power.
     */
    private final Integer indexOfPower;

    /**
     * Constructor method for his class.
     */
    public LWXProductionPower(LWResource consumedResource, Integer numberOfResourceToPay,
                              Integer numberOfResourceToProduce, LWProducible produced, Integer indexOfPower) {
        this.consumedResource = consumedResource;
        this.numberOfResourceToPay = numberOfResourceToPay;
        this.numberOfResourceToProduce = numberOfResourceToProduce;
        this.produced = produced;
        this.indexOfPower = indexOfPower;
    }

    /**
     * Getter method for the resources consumed by this power.
     */
    public LWResource getConsumedResource() {
        return consumedResource;
    }

    /**
     * Getter method for the amount of resources consumed by this power.
     */
    public Integer getNumberOfResourceToPay() {
        return numberOfResourceToPay;
    }

    /**
     * Getter method for the amount of resources produced by this power.
     */
    public Integer getNumberOfResourceToProduce() {
        return numberOfResourceToProduce;
    }

    /**
     * Getter method for the resources produced by this power.
     */
    public LWProducible getProduced() {
        return produced;
    }

    /**
     * Getter method for the index of this power.
     */
    public Integer getIndexOfPower() {
        return indexOfPower;
    }
}
