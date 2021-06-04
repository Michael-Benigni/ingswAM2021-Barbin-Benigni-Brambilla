package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class YourTurnUP implements ViewUpdate {
    private final String additionalMsg;

    public YourTurnUP(String additionalMsg) {
        this.additionalMsg = additionalMsg;
    }

    @Override
    public void update(View view) {
        view.getUI ().notifyMessage ("It's your Turn!");
        if (additionalMsg != null)
            view.getUI ().notifyMessage (additionalMsg);
        view.getUI ().nextInputRequest ();
    }
}
