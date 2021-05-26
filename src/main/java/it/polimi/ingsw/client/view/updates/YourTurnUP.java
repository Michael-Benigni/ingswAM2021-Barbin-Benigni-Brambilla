package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;

public class YourTurnUP implements ViewUpdate {
    @Override
    public void update(View view) {
        view.getUI ().notifyMessage ("It's your Turn!");
        view.readyForNextMove ();
    }
}
