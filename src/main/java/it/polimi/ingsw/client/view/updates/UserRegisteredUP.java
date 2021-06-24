package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.lightweightmodel.InfoMatch;

public class UserRegisteredUP implements ViewUpdate {
    private final boolean isLeader;
    private final int numUser;
    private final int numWaitingRoom;
    private final String username;
    private final int numUsers;

    public UserRegisteredUP(boolean isLeader, int numUser, int numWaitingRoom, String username, int numUsers) {
        this.isLeader = isLeader;
        this.numUser = numUser;
        this.numWaitingRoom = numWaitingRoom;
        this.username = username;
        this.numUsers = numUsers;
    }

    @Override
    public void update(Controller controller) {
        InfoMatch infoMatch = controller.getModel ().getInfoMatch ();
        infoMatch.setIsLeader(isLeader);
        infoMatch.setYourUsername (username);
        infoMatch.setRoomID (numWaitingRoom);
        infoMatch.setWaitingRoomSize(numUsers);
        controller.getUI ().notifyRegistration (isLeader, numUser);
    }
}
