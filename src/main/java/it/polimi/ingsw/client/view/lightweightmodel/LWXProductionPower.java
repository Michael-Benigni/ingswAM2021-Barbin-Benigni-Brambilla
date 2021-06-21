package it.polimi.ingsw.client.view.lightweightmodel;

public class LWXProductionPower {

    private LWResource consumedResource;
    private Integer numberOfResourceToPay;
    private Integer numberOfResourceToProduce;
    private LWProducible produced;
    private Integer indexOfPower;

    public LWXProductionPower(LWResource consumedResource, Integer numberOfResourceToPay,
                              Integer numberOfResourceToProduce, LWProducible produced, Integer indexOfPower) {
        this.consumedResource = consumedResource;
        this.numberOfResourceToPay = numberOfResourceToPay;
        this.numberOfResourceToProduce = numberOfResourceToProduce;
        this.produced = produced;
        this.indexOfPower = indexOfPower;
    }

    public LWResource getConsumedResource() {
        return consumedResource;
    }

    public Integer getNumberOfResourceToPay() {
        return numberOfResourceToPay;
    }

    public Integer getNumberOfResourceToProduce() {
        return numberOfResourceToProduce;
    }

    public LWProducible getProduced() {
        return produced;
    }

    public Integer getIndexOfPower() {
        return indexOfPower;
    }
}
