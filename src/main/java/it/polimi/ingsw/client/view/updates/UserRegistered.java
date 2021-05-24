package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.server.view.ViewUpdate;

public class UserRegistered implements ViewUpdate {
    private final int numUser;
    private final int numWaitingRoom;

    public UserRegistered(int numUser, int numWaitingRoom) {
        this.numUser = numUser;
        this.numWaitingRoom = numWaitingRoom;
    }

    @Override
    public void update(View view) {
    }
}
