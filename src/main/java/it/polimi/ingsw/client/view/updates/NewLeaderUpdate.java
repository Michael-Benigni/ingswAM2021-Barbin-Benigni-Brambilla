package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class NewLeaderUpdate implements ViewUpdate{

    @Override
    public void update(Controller controller) {
        controller.getModel().getInfoMatch().setIsLeader(true);
        controller.getUI().onLeaderDisconnected();
    }
}
