package it.polimi.ingsw.client.view.states;

public class PlayState extends ClientState {
    private enum TurnType {
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

        @Override
        public String toString() {
            return (cmd + ": " + desc + "\n");
        }

        private static String getPossibilities() {
            return TurnType.BUY_CARD.toString () + TurnType.MARKET + TurnType.PRODUCTION;
        }
    }

    private String getAllResourceTypes() {
        return "COIN, STONE, SERVANT, SHIELD";
    }

    public PlayState() {
        addAvailableMove ("T", chooseTurnTypeMove (), "CHOOSE THE TYPE OF TURN TO PLAY");
        addAvailableMove ("L", leaderCardMove (), "PLAY OR DISCARD A LEADER CARD");
        addAvailableMove ("E", endTurnMove (), "TERMINATE YOUR TURN");
        addAvailableMove ("S", swapDepotMove (), "SWAP 2 DEPOTS");
    }

    private Move chooseTurnTypeMove() {
        return (interpreter, interlocutor) -> {
            String turnType = interpreter.listen ();
            addAvailableMoves (turnType);
            interlocutor.write (super.menu ());
            return null;
        };
    }

    private void addAvailableMoves(String turnType) {
        switch (turnType) {
            case "P": {
                addAvailableMove ("SP", startProductionMove(), "START PRODUCTION");
                addAvailableMove ("CP", cardProductionMove()," CARD PRODUCTION");
                addAvailableMove ("BP", boardProductionMove(),"BOARD PRODUCTION");
                addAvailableMove ("EXP", extraBoardProductionMove(),"EXTRA-BOARD PRODUCTION");
                addAvailableMove ("EP", endProductionMove(), "END PRODUCTION");
                break;
            }
            case "B": {
                addAvailableMove ("B", buyCardMove(), "BUY A CARD FROM THE CARDS GRID");
                break;
            }
            case "M": {
                addAvailableMove ("W", transformWhiteMarbleMove (), "TRANSFORM A WHITE MARBLE (only after leader card activation)");
                addAvailableMove ("M", marketMove(), "GO TO MARKET");
                addAvailableMove ("T", moveResourcesMove(), "MOVE RESOURCES TO AND FROM WAREHOUSE");
                addAvailableMove ("E", warehouseMove (), "");
                break;
            }
            default:
        }
    }

    @Override
    public String menu() {
        return "If is your first turn press \"F\"! Otherwise...\n" + super.menu ();
    }

    @Override
    public ClientState getNextState() {
        return null;
    }

    private Move endTurnMove() {
        return null;
    }

    private Move leaderCardMove() {
        return null;
    }

    private Move warehouseMove() {
        return null;
    }

    private Move swapDepotMove() {
        return null;
    }

    private Move transformWhiteMarbleMove() {
        return null;
    }

    private Move startProductionMove() {
        return null;
    }

    private Move moveResourcesMove() {
        return null;
    }

    private Move marketMove() {
        return null;
    }

    private Move buyCardMove() {
        return null;
    }

    private Move endProductionMove() {
        return null;
    }

    private Move extraBoardProductionMove() {
        return null;
    }

    private Move boardProductionMove() {
        return null;
    }

    private Move cardProductionMove() {
        return null;
    }
}
