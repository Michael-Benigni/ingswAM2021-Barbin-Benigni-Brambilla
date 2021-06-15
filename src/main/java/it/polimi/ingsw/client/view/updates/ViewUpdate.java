package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.utils.network.Sendable;

public interface ViewUpdate extends Sendable {
    void update(Controller controller);
}
