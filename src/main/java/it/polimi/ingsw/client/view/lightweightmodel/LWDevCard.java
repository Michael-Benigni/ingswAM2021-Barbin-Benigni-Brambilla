package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.cli.Colour;

/**
 * Class that represents the view of a development card.
 */
public class LWDevCard {

    /**
     * Identification number of this card.
     */
    private final Integer id;

    private final String description;
    private final Colour colour;
    private final Integer level;

    /**
     * Integer that represents the index of the slot in which this card is placed.
     */
    private final Integer indexInSlot;

    /**
     * Constructor method of this class.
     */
    public LWDevCard(Integer id, String description, Colour colour, Integer level, Integer indexInSlot) {
        this.id = id;
        this.indexInSlot = indexInSlot;
        this.description = description;
        this.colour = colour;
        this.level = level;
    }

    /**
     * Getter method for the ID of this card.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter method for the colour of this card.
     */
    public Colour getColour() {
        return this.colour ;
    }

    /**
     * Getter method for the level of this card.
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * Override method of "equals()" that compares two instances of this class.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LWDevCard)) return false;
        LWDevCard lwDevCard = (LWDevCard) o;
        return getId() == lwDevCard.getId();
    }

    /**
     * Getter method for the description of this card.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Getter method for the index of the slot in which this card is placed.
     */
    public Integer getIndexInSlot() {
        return indexInSlot;
    }
}
