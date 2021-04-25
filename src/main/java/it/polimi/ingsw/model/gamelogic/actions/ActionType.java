package it.polimi.ingsw.model.gamelogic.actions;

public enum ActionType {
    START("start"),
    MUTUAL_EX("mutualExclusive"),
    ANYTIME("anytime"),
    END("end");
    //TODO: EmptyResourceAction (?)

    private String type;

    ActionType(String type) {
        this.type = type;
    }
}
