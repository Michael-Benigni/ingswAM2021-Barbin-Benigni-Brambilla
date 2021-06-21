package it.polimi.ingsw.client.view.lightweightmodel;

public class LWWMPower {

    private final Integer powerWMIndex;
    private final LWResource resourceObtained;

    public LWWMPower(Integer powerWMIndex, LWResource resourceObtained) {
        this.powerWMIndex = powerWMIndex;
        this.resourceObtained = resourceObtained;
    }

    public Integer getPowerWMIndex() {
        return powerWMIndex;
    }

    public LWResource getResourceObtained() {
        return resourceObtained;
    }
}
