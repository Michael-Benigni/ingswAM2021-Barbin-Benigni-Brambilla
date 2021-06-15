package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.cli.Colour;

public class LWDevCard {
    private final int id;
    private final String description;
    private final Colour colour;
    private final int level;
    private final int indexInSlot;

    public LWDevCard(int id, String description, Colour colour, int level, int indexInSlot) {
        this.id = id;
        this.indexInSlot = indexInSlot;
        this.description = description;
        this.colour = colour;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public Colour getColour() {
        return this.colour ;
    }

    public int getLevel() {
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

    public int getIndexInSlot() {
        return indexInSlot;
    }
}
