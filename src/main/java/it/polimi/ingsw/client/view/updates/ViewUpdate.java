package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.utils.network.Sendable;

public interface ViewUpdate extends Sendable {
    void update(View view);
}
