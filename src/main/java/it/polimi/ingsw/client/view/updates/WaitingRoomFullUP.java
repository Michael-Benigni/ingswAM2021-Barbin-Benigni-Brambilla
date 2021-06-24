package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class WaitingRoomFullUP implements ViewUpdate {
    @Override
    public void update(Controller controller) {
        controller.getUI ().notifyMessage ("The room is full, you can start the match!");
    }
}
