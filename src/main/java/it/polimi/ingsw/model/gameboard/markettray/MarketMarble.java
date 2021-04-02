package it.polimi.ingsw.model.gameboard.markettray;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import it.polimi.ingsw.model.gameresources.Resource;

/**
 * class that models the fishable marbles from the market tray
 */
public class MarketMarble {

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
    Resource getCorrespondentResource() throws NegativeResourceAmountException {
        Resource resourceCopy = resource.copyResource();
        return resourceCopy;
    }
}
