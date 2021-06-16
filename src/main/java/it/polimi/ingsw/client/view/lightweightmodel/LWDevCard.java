package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.cli.Colour;

public class LWDevCard {
    private final Integer id;
    private final String description;
    private final Colour colour;
    private final Integer level;
    private final Integer indexInSlot;

    public LWDevCard(Integer id, String description, Colour colour, Integer level, Integer indexInSlot) {
        this.id = id;
        this.indexInSlot = indexInSlot;
        this.description = description;
        this.colour = colour;
        this.level = level;
    }

    public Integer getId() {
        return id;
    }

    public Colour getColour() {
        return this.colour ;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LWDevCard)) return false;
        LWDevCard lwDevCard = (LWDevCard) o;
        return getId() == lwDevCard.getId();
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getIndexInSlot() {
        return indexInSlot;
    }
}
