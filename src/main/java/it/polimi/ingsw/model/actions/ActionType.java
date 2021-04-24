package it.polimi.ingsw.model.actions;

public enum ActionType {
    MARKET("market"),
    STORE("storeResource"),
    DISCARD("discardResource"),
    PRODUCTION("production"),
    BUY_CARD("buyCard"),
    MOVE_RESOURCES_DEPOTS("moveResources"),
    LEADER("leader"),
    END("end");

    private String type;

    ActionType(String type) {
        this.type = type;
    }
}
/**
 * MARKET -AFTER-> START, LEADER
 * MOVE_RESOURCES_DEPOTS -> ALWAYS AFTER START, LEADER
 * STORE_FROM_TEMPORARY -> MARKET, LEADER
 * DISCARD_RESOURCES -> MARKET, LEADER
 * BUY_CARD -> START, LEADER (also payment ???)
 * (?) PAY_CARD -> BUY_CARD, LEADER
 * PRODUCTION -> PAY_PRODUCTION, START, LEADER
 * (?) PAY_PRODUCTION -> PRODUCTION, LEADER
 * LEADER(both discarded and played) -> ALWAYS AFTER START
 */
