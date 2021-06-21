package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.exceptions.UnavailableMoveName;
import it.polimi.ingsw.client.view.moves.PlayMove;

public class PlayState extends ClientState {
    public enum TurnType {
        PRODUCTION("P", "Production Turn"),
        MARKET("M", "Market Turn"),
        BUY_CARD("B", "Buy Card Turn"),
        FIRST_TURN("F", "First Turn");

        private final String cmd;
        private final String desc;

        TurnType(String cmd, String desc) {
            this.cmd = cmd;
            this.desc = desc;
        }

        public static TurnType get(String turnType) throws UnavailableMoveName {
            TurnType[] types = TurnType.values ();
            for (TurnType type : types) {
                if (type.cmd.equals (turnType))
                    return type;
            }
            throw new UnavailableMoveName ();
        }

        @Override
        public String toString() {
            return (cmd + ": " + desc);
        }

        public static String getPossibilities() {
            return TurnType.BUY_CARD + "\n" + TurnType.MARKET + "\n" + TurnType.PRODUCTION + "\n";
        }
    }

    public static String getAllResourceTypes() {
        return "COIN, STONE, SERVANT, SHIELD";
    }

    public PlayState() {
        addAvailableMove (PlayMove.DISCARD_LEADER_CARD_FIRST_TURN.getCmd (), "DISCARD A LEADER CARD AT THE BEGINNING OF THE GAME (only in the first turn)");
        addAvailableMove (PlayMove.CHOOSE_TURN_TYPE.getCmd (), "CHOOSE THE TYPE OF TURN TO PLAY");
        addAvailableMove (PlayMove.LEADER.getCmd (), "PLAY OR DISCARD A LEADER CARD");
        addAvailableMove (PlayMove.END_TURN.getCmd (), "TERMINATE YOUR TURN");
        addAvailableMove (PlayMove.SWAP_DEPOTS.getCmd (), "SWAP 2 DEPOTS");
        addAvailableMove (PlayMove.SHOW_GAME_BOARD.getCmd (), "SHOW THE GAME BOARD");
        addAvailableMove (PlayMove.SHOW_PERSONAL_BOARD.getCmd (), "SHOW THE PERSONAL BOARD");
        addAvailableMove (PlayMove.WAREHOUSE.getCmd (), "PICK YOUR INITIAL RESOURCES (ALSO YOU CAN REMOVE THEM IF YOU WANT TO CHANGE YOUR CHOICE)");
        addAvailableMove (PlayMove.MOVE_RESOURCES.getCmd (), "MOVE RESOURCES BETWEEN WAREHOUSE AND TEMPORARY CONTAINER");
    }


    public static void addAvailableMoves(TurnType turnType) {
        switch (turnType) {
            case PRODUCTION: {
                addAvailableMove (PlayMove.START_PRODUCTION.getCmd (),  "START PRODUCTION");
                addAvailableMove (PlayMove.CARD_PRODUCTION.getCmd (), "CARD PRODUCTION");
                addAvailableMove (PlayMove.BOARD_PRODUCTION.getCmd (), "BOARD PRODUCTION");
                addAvailableMove (PlayMove.EXTRA_BOARD_PRODUCTION.getCmd (), "EXTRA-BOARD PRODUCTION");
                addAvailableMove (PlayMove.END_PRODUCTION.getCmd (), "END PRODUCTION");
                break;
            }
            case BUY_CARD: {
                addAvailableMove (PlayMove.BUY_CARD.getCmd (), "BUY A CARD FROM THE CARDS GRID");
                break;
            }
            case MARKET: {
                addAvailableMove (PlayMove.WHITE_MARBLE.getCmd (), "TRANSFORM A WHITE MARBLE (only after leader card activation)");
                addAvailableMove (PlayMove.MARKET.getCmd (), "GO TO MARKET");
                break;
            }
            default:
        }
    }

    public static String menu() {
        return "If is your first turn press \"F\"! Otherwise...\n" + ClientState.menu ();
    }

    @Override
    public ClientState getNextState() {
        return new GameOverState ();
    }
}

