package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.lightweightmodel.InfoMatch;

public class UserRegisteredUP implements ViewUpdate {
    private final int numUsers;
    private final int numWaitingRoom;
    private final String username;
    private final boolean areYou;

    public UserRegisteredUP(int numUsers, int numWaitingRoom, String username, boolean areYou) {
        this.numUsers = numUsers;
        this.numWaitingRoom = numWaitingRoom;
        this.username = username;
        this.areYou = areYou;
    }

    @Override
    public void update(View view) {
        InfoMatch infoMatch = view.getModel ().getInfoMatch ();
        if (areYou)
            infoMatch.setYourUsername (username);
        else
            infoMatch.addOtherPlayerUsername (username);
        infoMatch.setRoomID (numWaitingRoom);
        infoMatch.setTotalNumPlayers (numUsers);
        view.getUI ().notifyMessage (numUsers + "° user registered, in the waiting room n° " + numWaitingRoom + ", with name: " + username);
        view.getUI ().nextInputRequest ();
    }
}
