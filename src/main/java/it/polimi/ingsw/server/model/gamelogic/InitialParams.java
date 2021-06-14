package it.polimi.ingsw.server.model.gamelogic;

public class InitialParams {
    private final int initialResources;
    private final int initialFaithPoints;

    public InitialParams(int initialResources, int initialFaithPoints) {
        this.initialResources = initialResources;
        this.initialFaithPoints = initialFaithPoints;
    }

    int getInitialResources() {
        return initialResources;
    }

    int getInitialFaithPoints() {
        return initialFaithPoints;
    }
}
