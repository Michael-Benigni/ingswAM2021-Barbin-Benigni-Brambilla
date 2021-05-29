package it.polimi.ingsw.server.model.gamelogic.actions;


public interface Producer {

    boolean isAvailableForProduction();

    void setAvailableForProduction(boolean availableForProduction);
}
