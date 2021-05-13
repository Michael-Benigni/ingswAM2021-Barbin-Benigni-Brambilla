package it.polimi.ingsw.server.utils.network;

import it.polimi.ingsw.server.utils.config.JsonHandler;

public interface JsonTrasmittable {
    default String transmit() {
        return JsonHandler.asJson(this);
    }
}
