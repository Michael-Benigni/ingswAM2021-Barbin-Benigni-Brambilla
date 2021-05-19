package it.polimi.ingsw.client.view;

import it.polimi.ingsw.utils.network.Header;

public enum ToClient implements Header {
    ;

    private final String headerStr;

    ToClient (String headerStr) {
        this.headerStr = headerStr;
    }
}
