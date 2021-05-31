package it.polimi.ingsw.client.view.lightweightmodel;

import java.util.Objects;

public class LWDevCard {
    private final int id;
    private final String description;

    public LWDevCard(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LWDevCard)) return false;
        LWDevCard lwDevCard = (LWDevCard) o;
        return getId() == lwDevCard.getId();
    }

    public String getDescription() {
        return "description";
    }
}
