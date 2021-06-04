package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.cli.Colour;

public class LWResource {
    private Type type;
    private int amount;

    LWResource(Type type, int amount) {
        this.type = type;
        this.amount = amount;
    }


    public Type getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
/**
 *
 */
enum Type {
    SERVANT ("SERVANT", Colour.PURPLE), STONE ("STONE", Colour.PURPLE), SHIELD ("SHIELD", Colour.BLUE), COIN ("COIN", Colour.YELLOW), EMPTY ("empty", null);

    private final String type;

    private final Colour colour;
    Type(String type, Colour colour) {
        this.type = type;
        this.colour = colour;
    }

    @Override
    public String toString() {
        return colour.escape () + type + Colour.RESET.escape ();
    }

}