package it.polimi.ingsw.server.model.exception;

public class NotExistingExtraProductionPower extends Exception{
    public NotExistingExtraProductionPower() {
        super("Impossible to find the extra production power: the provided index is less than zero or exceeds the number of extra production power");
    }
}
