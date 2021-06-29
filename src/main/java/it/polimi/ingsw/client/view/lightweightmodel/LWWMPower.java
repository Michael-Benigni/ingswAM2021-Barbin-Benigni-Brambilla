package it.polimi.ingsw.client.view.lightweightmodel;

/**
 * Class that represents the view of a "Transform White Marble" power.
 */
public class LWWMPower {

    /**
     * Index of this power.
     */
    private final Integer powerWMIndex;

    /**
     * Resource that can be produced with this power.
     */
    private final LWResource resourceObtained;

    /**
     * Constructor method of this class.
     */
    public LWWMPower(Integer powerWMIndex, LWResource resourceObtained) {
        this.powerWMIndex = powerWMIndex;
        this.resourceObtained = resourceObtained;
    }

    /**
     * Getter method for the index of this power.
     */
    public Integer getPowerWMIndex() {
        return powerWMIndex;
    }

    /**
     * Getter method for the resource that can be produced by this power.
     */
    public LWResource getResourceObtained() {
        return resourceObtained;
    }
}
