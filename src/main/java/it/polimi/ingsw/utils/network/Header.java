package it.polimi.ingsw.utils.network;

public interface Header {

    /**
     * Headers of messages that can be received from both Client and Server
     */
    enum Common implements Header{
        ACK("ack"),
        ERROR ("error"),
        VALID ("valid"),
        QUIT ("quit");

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
        SLOT_DEVCARD_UPDATE("slot_devcard_update"),
        LEADER_CARDS_UPDATE("init_leader_cards"),
        STRONGBOX_UPDATE("strongbox_update"),
        MARKET_UP ("market_up"),
        TEMP_CONTAINER_UPDATE("temp_container_update"),
        GET_PENALTY_UPDATE("get_penalty_update"),
        FAITH_TRACK_UPDATE("faith_track_update"),
        PLAYER_INFO_FOR_OTHERS ("player_position"),
        WAIT_YOUR_TURN ("wait_your_turn"),
        LAST_ROUND_UP ("last_round"),
        GAME_OVER_UP ("game_over"),
        SET_NUM_PLAYERS ("set_num_players"),
        INITIAL_FAITH_TRACK_UPDATE("initial_faith_track_update"),
        DISCONNECTION_UP ("disconnection"),
        GENERIC_INFO ("tile_already_activated"),
        RECONNECTION_RESPONSE ("reconnection_response"),
        ADD_EXTRA_PRODUCTION_POWER_UPDATE("add_extra_production_power_update"),
        ADD_WM_POWER_UPDATE("add_wm_power_update");

        private final String headerStr;

        ToClient (String headerStr) {
            this.headerStr = headerStr;
        }
    }


    /**
     * Headers of messages that can be received from Server and sent by Client
     */
    enum ToServer implements Header {
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
        START_MATCH ("start_match"),
        START_PRODUCTION ("start_production"),
        NEW_ROOM ("new_room"),
        EXISTING_ROOM ("existing_room"),
        DISCARD_LEADER_1ST_TURN ("discard_leader_card_1st_turn");

        private String headerStr;

        ToServer(String headerStr) {
            this.headerStr = headerStr;
        }
    }
}
