package it.polimi.ingsw.server.utils.network;

public enum Header {
    NEW_USER("new_user"),
    SET_USERNAME("set_username"),
    SET_NUM_PLAYERS("set_num_players"),

    ;

    private String headerStr;

    Header(String headerStr) {
        this.headerStr = headerStr;
    }
}
