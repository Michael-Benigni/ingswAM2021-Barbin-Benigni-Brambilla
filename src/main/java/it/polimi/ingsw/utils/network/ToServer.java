package it.polimi.ingsw.utils.network;

public enum ToServer implements Header {
    NEW_USER("new_user"),
    SET_USERNAME("set_username"),
    SET_NUM_PLAYERS("set_num_players"),

    BOARD_PRODUCTION("board_production"),
    BUY_CARD("buy_card"),
    DISCARD_ALL_RESOURCES("discard_all_resources"),
    END_PRODUCTION("end_production"),
    END_TURN("end_turn"),
    EXTRA_PRODUCTION("extra_production"),
    LEADER("leader"),
    MARKET("market"),
    PRODUCTION_CARD("production_card"),
    STRONGBOX("strongbox"),
    SWAP_DEPOTS("swap_depots"),
    TEMP_CONTAINER("Temporary_container"),
    TRANSFORM_WHITE_MARBLE("transform_white_marble"),
    WAREHOUSE("warehouse"),
    QUIT ("quit"),
    ACK ("ack"),
    ERROR ("error");

    private String headerStr;

    ToServer(String headerStr) {
        this.headerStr = headerStr;
    }
}
