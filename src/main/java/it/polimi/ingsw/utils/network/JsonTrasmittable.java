package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.config.JsonHandler;

public interface JsonTrasmittable {
    default String transmit() {
        return JsonHandler.asJson(this);
    }
}
