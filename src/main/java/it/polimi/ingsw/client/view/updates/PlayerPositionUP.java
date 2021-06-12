package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class PlayerPositionUP implements ViewUpdate {
    private final int position;
    private final String name;

    public PlayerPositionUP(int position, String name) {
        this.position = position;
        this.name = name;
    }

    @Override
    public void update(View view) {
        view.getModel ().getInfoMatch ().putNewPlayer(position, name);
    }
}
