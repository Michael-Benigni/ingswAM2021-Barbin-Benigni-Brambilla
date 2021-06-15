package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.InfoMatch;

public class UserRegisteredUP implements ViewUpdate {
    private final int numUsers;
    private final int numWaitingRoom;
    private final String username;

    public UserRegisteredUP(int numUsers, int numWaitingRoom, String username) {
        this.numUsers = numUsers;
        this.numWaitingRoom = numWaitingRoom;
        this.username = username;
    }

    @Override
    public void update(Controller controller) {
        InfoMatch infoMatch = controller.getModel ().getInfoMatch ();
        infoMatch.setYourUsername (username);
        infoMatch.setRoomID (numWaitingRoom);
        infoMatch.setTotalNumPlayers (numUsers);
        controller.getUI ().notifyMessage (numUsers + "° user registered, in the waiting room n° " + numWaitingRoom + ", with name: " + username);
        controller.getUI ().nextInputRequest ();
    }
}
