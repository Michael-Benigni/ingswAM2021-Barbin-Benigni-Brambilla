package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class PlayerPositionUP implements ViewUpdate {
    private final int positionInTurn;
    private final String name;

    public PlayerPositionUP(int positionInTurn, String name) {
        this.positionInTurn = positionInTurn;
        this.name = name;
    }

    @Override
    public void update(View view) {
        view.getModel ().getInfoMatch ().putNewPlayer(positionInTurn, name);
    }
}
