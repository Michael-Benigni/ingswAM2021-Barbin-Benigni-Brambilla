package it.polimi.ingsw.utils.network;

public interface Header {

    /**
     * Headers of messages that can be received from both Client and Server
     */
    enum Common implements Header{
        ACK("ack"),
        ERROR ("error"),
        VALID ("valid");

        private final String stringInfo;

        Common(String stringInfo) {
            this.stringInfo = stringInfo;
        }
    }


    /**
     * Headers of messages that can be received from Client and sent by Server
     */
    enum ToClient implements Header {
        WAREHOUSE_UPDATE("warehouse_update"),
        USER_REGISTERED ("user_registered"),
        SHOW_INITIAL_GRID("show_initial_grid"),
        REMOVE_SHOW_GRID("remove_show_grid"),
        TURN_POSITION_UPDATE ("turn_position_update"),
        YOUR_TURN ("your_turn"),
        FULL_ROOM ("full_room"),
        SLOT_DEVCARD_UPDATE("slot_devcard_update");


        private final String headerStr;

        ToClient (String headerStr) {
            this.headerStr = headerStr;
        }
    }


    /**
     * Headers of messages that can be received from Server and sent by Client
     */
    enum ToServer implements Header {
        QUIT ("quit"),
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
        START_MATCH ("start_match");

        private String headerStr;

        ToServer(String headerStr) {
            this.headerStr = headerStr;
        }
    }
}
