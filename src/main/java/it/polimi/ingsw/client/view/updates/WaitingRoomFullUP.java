package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class WaitingRoomFullUP implements ViewUpdate {
    @Override
    public void update(View view) {
        view.getUI ().notifyMessage ("The room is full, you can start the match!");
        view.getUI ().nextInputRequest();
    }
}
