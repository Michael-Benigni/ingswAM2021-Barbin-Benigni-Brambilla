package it.polimi.ingsw.model.gameresources.markettray;

/**
 * class that models the fishable marbles from the market tray
 */
class MarketMarble {

    private final MarbleColour colour;
    private final Resource resource;

    /**
     * constructor of the class MarketMarble
     * @param colour -> attribute that indicates the marble's colour
     * @param resource -> attribute that associates the marble's colour to the correspondent resource
     */
    MarketMarble(MarbleColour colour, Resource resource) {
        this.colour = colour;
        this.resource = resource;
    }

    /**
     * this method creates a copy of the marble's resource
     * @return the created copy to the caller
     */
    Resource getCorrespondentResource() throws CloneNotSupportedException {
        Resource resourceCopy = resource.copyResource();
        return resourceCopy;
    }

    private MarbleColour getColour () {
        return this.colour;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
