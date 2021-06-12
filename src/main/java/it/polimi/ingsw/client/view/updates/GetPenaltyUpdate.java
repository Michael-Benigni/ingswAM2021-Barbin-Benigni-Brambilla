package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class GetPenaltyUpdate implements ViewUpdate {

    private int penalty;

    public GetPenaltyUpdate(int penalty) {
        this.penalty = penalty;
    }

    @Override
    public void update(View view){
        if (penalty > 0)
            view.getUI().notifyMessage("you have discarded some resources, " +
                "your adversaries move on the faith track by " +
                penalty + " steps");
    }
}
