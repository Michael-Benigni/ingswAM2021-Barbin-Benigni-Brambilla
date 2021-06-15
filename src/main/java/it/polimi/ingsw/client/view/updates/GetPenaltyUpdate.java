package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class GetPenaltyUpdate implements ViewUpdate {

    private int penalty;

    public GetPenaltyUpdate(int penalty) {
        this.penalty = penalty;
    }

    @Override
    public void update(Controller controller){
        if (penalty > 0)
            controller.getUI().notifyMessage("you have discarded some resources, " +
                "your adversaries move on the faith track by " +
                penalty + " steps");
    }
}
