package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class LastRoundUP implements ViewUpdate {
    @Override
    public void update(View view) {
        view.getUI ().notifyMessage ("The Game is finishing. This is the last round. When the last player of the " +
                "round terminates his turn the game will end.");
    }
}
