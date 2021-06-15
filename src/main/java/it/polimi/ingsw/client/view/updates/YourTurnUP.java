package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class YourTurnUP implements ViewUpdate {
    private final String additionalMsg;

    public YourTurnUP(String additionalMsg) {
        this.additionalMsg = additionalMsg;
    }

    @Override
    public void update(Controller controller) {
        controller.getUI ().notifyMessage ("It's your Turn!");
        if (additionalMsg != null)
            controller.getUI ().notifyMessage (additionalMsg);
        controller.getUI ().nextInputRequest ();
    }
}
