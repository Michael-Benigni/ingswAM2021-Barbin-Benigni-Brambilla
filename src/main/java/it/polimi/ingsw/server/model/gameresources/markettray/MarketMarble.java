package it.polimi.ingsw.server.model.gameresources.markettray;

import it.polimi.ingsw.server.model.gameresources.Resource;

import java.util.Objects;

/**
 * class that models the fishable marbles from the market tray
 */
public class MarketMarble implements Cloneable {

    private final MarbleColour colour;
    private final Resource resource;

    /**
     * constructor of the class MarketMarble
     * @param colour -> attribute that indicates the marble's colour
     * @param resource -> attribute that associates the marble's colour to the correspondent resource
     */
    public MarketMarble(MarbleColour colour, Resource resource) {
        this.colour = colour;
        this.resource = resource;
    }

    /**
     * this method creates a copy of the marble's resource
     * @return the created copy to the caller
     */
    Resource getCorrespondentResource() {
        return (Resource) resource.clone();
    }

    MarbleColour getColour() {
        return this.colour;
    }

    @Override
    protected MarketMarble clone() {
        try {
            return (MarketMarble) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketMarble marble = (MarketMarble) o;
        return colour == marble.colour && Objects.equals(resource, marble.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, resource);
    }
}
