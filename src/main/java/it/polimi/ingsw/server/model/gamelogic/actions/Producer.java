package it.polimi.ingsw.server.model.gamelogic.actions;

public abstract class Producer {
    private boolean availableForProduction;

    public boolean isAvailableForProduction() {
        return availableForProduction;
    }

    protected void setAvailableForProduction(boolean availableForProduction) {
        this.availableForProduction = availableForProduction;
    }
}
