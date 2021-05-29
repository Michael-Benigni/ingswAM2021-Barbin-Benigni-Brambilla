package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.ui.cli.*;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

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
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.END_TURN);
            return writer.write ();
        };
    }

    private Move leaderCardMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.LEADER);
            StringRequest playOrRemove = new StringRequest ("Digit \"play\" or \"discard\" according to the action that you want to perform", "playOrDiscard");
            writer = playOrRemove.handleInput (interlocutor,interpreter, writer);
            IntegerRequest numInSlot = new IntegerRequest ("Indicate the position of the card that you want to use", "numInSlot");
            writer = numInSlot.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private Move warehouseMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.WAREHOUSE);
            StringRequest playOrStore = new StringRequest ("Digit \"store\" or \"remove\" according to the action that you want to perform", "playOrRemove");
            writer = playOrStore.handleInput (interlocutor,interpreter, writer);
            IntegerRequest depotIdx = new IntegerRequest ("Indicate the number of depot to use", "depotIdx");
            writer = depotIdx.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private Move swapDepotMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.SWAP_DEPOTS);
            IntegerRequest depot1 = new IntegerRequest ("Indicate the number of depot 1 to swap", "depot1");
            writer = depot1.handleInput (interlocutor, interpreter, writer);
            IntegerRequest depot2 = new IntegerRequest ("Indicate the number of depot 2 to swap", "depot2");
            writer = depot2.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private Move transformWhiteMarbleMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.TRANSFORM_WHITE_MARBLE);
            IntegerRequest numInSlot = new IntegerRequest ("Indicate the position of the resource to obtain", "resourceIdx");
            writer = numInSlot.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private Move startProductionMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.START_PRODUCTION);
            return writer.write ();
        };
    }

    private Move moveResourcesMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.TEMP_CONTAINER);
            StringRequest playOrStore = new StringRequest ("Digit \"store\" or \"remove\" according to the action that you want to perform", "storeOrRemove");
            writer = playOrStore.handleInput (interlocutor,interpreter, writer);
            ResourceRequest resource = new ResourceRequest ("Choose the resource: digit the type between " + getAllResourceTypes () + " and the a amount to move", "resource");
            writer = resource.handleInput (interlocutor, interpreter, writer);
            IntegerRequest depotIdx = new IntegerRequest ("Indicate the number of depot to use", "depotIdx");
            writer = depotIdx.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private Move marketMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.MARKET);
            StringRequest rowOrColumn = new StringRequest ("Digit \"row\" or \"column\" according to from you want to get resources", "rowOrColumn");
            writer = rowOrColumn.handleInput (interlocutor,interpreter, writer);
            IntegerRequest numRowOrColumn = new IntegerRequest ("Choose the number of row or column", "numRowOrColumn");
            writer = numRowOrColumn.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private Move endProductionMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.END_PRODUCTION);
            return writer.write ();
        };
    }

    private Move buyCardMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.BUY_CARD);
            IntegerRequest row = new IntegerRequest ("Choose the number of row", "row");
            writer = row.handleInput (interlocutor, interpreter, writer);
            IntegerRequest column = new IntegerRequest ("Choose the number of column", "column");
            writer = column.handleInput (interlocutor, interpreter, writer);
            IntegerRequest slotIdx = new IntegerRequest ("Choose the number of the card slot index on which you want to place the new card", "slotIdx");
            writer = slotIdx.handleInput (interlocutor, interpreter, writer);
            return payments (interlocutor, interpreter, writer, "payActions").write ();
        };
    }

    private Move extraBoardProductionMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.EXTRA_PRODUCTION);
            IntegerRequest numExtraPower = new IntegerRequest ("Choose the number of the extra production power from those that you have active", "numExtraPower");
            writer = numExtraPower.handleInput (interlocutor, interpreter, writer);
            ResourceRequest resource = new ResourceRequest ("Choose the resource: digit the type between " + getAllResourceTypes () + " and the a amount to move", "resourceProduced");
            writer = resource.handleInput (interlocutor, interpreter, writer);
            return payments (interlocutor, interpreter, writer, "fromWhere").write ();
        };
    }

    private Move boardProductionMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.BOARD_PRODUCTION);
            ResourceRequest resource = new ResourceRequest ("Choose the resource: digit the type between " + getAllResourceTypes () + " and the a amount to move", "produced");
            writer = resource.handleInput (interlocutor, interpreter, writer);
            return payments (interlocutor, interpreter, writer, "payActions").write ();
        };
    }

    private Move cardProductionMove() {
        return (interpreter, interlocutor) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.PRODUCTION_CARD);
            IntegerRequest numSlot = new IntegerRequest ("Choose the number of slot with the card that you want to use for production", "numSlot");
            writer = numSlot.handleInput (interlocutor, interpreter, writer);
            return payments (interlocutor, interpreter, writer, "payActions").write ();
        };
    }

    private MessageWriter payments(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer, String nameProperty) {
        String addOrStop;
        do {
            PaymentRequest payment = new PaymentRequest ("", nameProperty);
            writer = payment.handleInput(interlocutor, interpreter, writer);
            interlocutor.write ("Digit \"A\" to add another payment, \"S\" to stop");
            addOrStop = interpreter.listen ();
        } while (addOrStop.equals ("A"));
        return writer;
    }
}
