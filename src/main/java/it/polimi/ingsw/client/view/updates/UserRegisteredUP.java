package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class UserRegisteredUP implements ViewUpdate {
    private final int numUser;
    private final int numWaitingRoom;
    private final String username;

    public UserRegisteredUP(int numUser, int numWaitingRoom, String username) {
        this.numUser = numUser;
        this.numWaitingRoom = numWaitingRoom;
        this.username = username;
    }

    @Override
    public void update(View view) {
        view.getUI ().notifyMessage (numUser + "° user registered, in the waiting room n° " + numWaitingRoom + ", with name: " + username);
        view.readyForNextMove ();
    }
}
