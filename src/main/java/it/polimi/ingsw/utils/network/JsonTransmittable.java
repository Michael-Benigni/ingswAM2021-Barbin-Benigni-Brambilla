package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.utils.config.JsonHandler;

public interface JsonTransmittable {
    default String transmit() {
        return JsonHandler.asJson(this);
    }
}
