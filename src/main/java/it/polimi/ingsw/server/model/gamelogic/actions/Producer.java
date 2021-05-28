package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.GameComponent;

public abstract class Producer extends GameComponent {
    private boolean availableForProduction;

    public boolean isAvailableForProduction() {
        return availableForProduction;
    }

    protected void setAvailableForProduction(boolean availableForProduction) {
        this.availableForProduction = availableForProduction;
    }
}
