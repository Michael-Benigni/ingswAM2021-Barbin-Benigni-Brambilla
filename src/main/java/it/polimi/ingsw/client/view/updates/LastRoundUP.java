package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class LastRoundUP implements ViewUpdate {
    @Override
    public void update(Controller controller) {
        controller.getUI ().notifyMessage ("The Game is finishing. This is the last round. When the last player of the " +
                "round terminates his turn the game will end.");
    }
}
