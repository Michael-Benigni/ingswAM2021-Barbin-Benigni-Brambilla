package it.polimi.ingsw.client.view.moves;

import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.exceptions.UnavailableMoveName;
import it.polimi.ingsw.client.view.states.PlayState;
import it.polimi.ingsw.client.view.ui.cli.*;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

public enum PlayMove implements MoveType {
    BOARD_PRODUCTION ("BOARD", boardProductionMove ()),
    BUY_CARD ("BUY", buyCardMove ()),
    CARD_PRODUCTION ("CARD", cardProductionMove ()),
    CHOOSE_TURN_TYPE ("T", chooseTurnTypeMove ()),
    END_PRODUCTION ("ENDP", endProductionMove ()),
    END_TURN ("END", endTurnMove ()),
    EXTRA_BOARD_PRODUCTION ("X", extraBoardProductionMove ()),
    LEADER ("L", leaderCardMove ()),
    MARKET ("M", marketMove ()),
    MOVE_RESOURCES ("R", moveResourcesMove ()),
    START_PRODUCTION ("SP", startProductionMove ()),
    SWAP_DEPOTS ("SW", swapDepotMove ()),
    WAREHOUSE ("W", warehouseMove ()),
    WHITE_MARBLE ("WM", transformWhiteMarbleMove ()),
    SHOW_PERSONAL_BOARD("SPB", showPersonalBoard()),
    SHOW_GAME_BOARD("SGB", showGameBoard()),
    SHOW_INFO_GAME("INFO", showInfoGame ()),
    SHOW_CARD_INFO("CI", showCardInfo()),
    SHOW_MENU("MENU", showMenu()),
    QUIT("QUIT", quitMove()) ;

    private static Move showMenu() {
        return (ui) -> {
            ui.printMenu ();
            return null;
        };
    }

    private static Move showCardInfo() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            interlocutor.write ("Choose the row and the column of the card in the grid: \nRow: ");
            String rowAsStr = interpreter.listen ();
            int row = Integer.parseInt (rowAsStr);
            interlocutor.write ("Column :");
            String columnAsStr = interpreter.listen ();
            int column = Integer.parseInt (columnAsStr);
            String desc = ui.getView ().getModel ().getBoard ().getGrid ().getCard(row, column).getDescription ();
            interlocutor.write (desc);
            return null;
        };
    }

    private static Move quitMove() {
        return (ui) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.QUIT);
            return writer.write ();
        };
    }

    private static Move showGameBoard() {
        return (ui) -> {
            ui.showGameBoard();
            return null;
        };
    }

    private static Move showInfoGame() {
        return (ui) -> {
            ui.showInfoGame ();
            return null;
        };
    }

    private static Move showPersonalBoard() {
        return (ui) -> {
            ui.showPersonalBoard ();
            return null;
        };
    }

    private final Move move;
    private final String cmd;

    PlayMove(String cmd, Move move) {
        this.move = move;
        this.cmd = cmd;
    }


    @Override
    public Move getMove() {
        return this.move;
    }

    @Override
    public String getCmd() {
        return this.cmd;
    }

    private static Move endTurnMove() {
        return (ui) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.END_TURN);
            return writer.write ();
        };
    }

    private static Move leaderCardMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.LEADER);
            StringRequest playOrRemove = new StringRequest ("Digit \"play\" or \"discard\" according to the action that you want to perform", "playOrDiscard");
            writer = playOrRemove.handleInput (interlocutor, interpreter, writer);
            IntegerRequest numInSlot = new IntegerRequest ("Indicate the position of the card that you want to use", "numInSlot");
            writer = numInSlot.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private static Move warehouseMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.WAREHOUSE);
            StringRequest playOrStore = new StringRequest ("Digit \"store\" or \"remove\" according to the action that you want to perform", "storeOrRemove");
            writer = playOrStore.handleInput (interlocutor, interpreter, writer);
            IntegerRequest depotIdx = new IntegerRequest ("Indicate the number of depot to use", "depotIdx");
            writer = depotIdx.handleInput (interlocutor, interpreter, writer);
            ResourceRequest resourceReq = new ResourceRequest ("Choose the resource to store", "resourceToPay");
            writer = resourceReq.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private static Move swapDepotMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.SWAP_DEPOTS);
            IntegerRequest depot1 = new IntegerRequest ("Indicate the number of depot 1 to swap", "depot1");
            writer = depot1.handleInput (interlocutor, interpreter, writer);
            IntegerRequest depot2 = new IntegerRequest ("Indicate the number of depot 2 to swap", "depot2");
            writer = depot2.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private static Move transformWhiteMarbleMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.TRANSFORM_WHITE_MARBLE);
            IntegerRequest numInSlot = new IntegerRequest ("Indicate the position of the resource to obtain", "resourceIdx");
            writer = numInSlot.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private static Move startProductionMove() {
        return (ui) -> {
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.START_PRODUCTION);
            return writer.write ();
        };
    }

    private static Move moveResourcesMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.TEMP_CONTAINER);
            StringRequest playOrStore = new StringRequest ("Digit \"store\" or \"remove\" according to the action that you want to perform", "storeOrRemove");
            writer = playOrStore.handleInput (interlocutor, interpreter, writer);
            ResourceRequest resource = new ResourceRequest ("Choose the resource: digit the type between " + PlayState.getAllResourceTypes () + " and the a amount to move", "resource");
            writer = resource.handleInput (interlocutor, interpreter, writer);
            IntegerRequest depotIdx = new IntegerRequest ("Indicate the number of depot to use", "depotIdx");
            writer = depotIdx.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private static Move marketMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.MARKET);
            StringRequest rowOrColumn = new StringRequest ("Digit \"row\" or \"column\" according to from you want to get resources", "rowOrColumn");
            writer = rowOrColumn.handleInput (interlocutor, interpreter, writer);
            IntegerRequest numRowOrColumn = new IntegerRequest ("Choose the number of row or column", "numRowOrColumn");
            writer = numRowOrColumn.handleInput (interlocutor, interpreter, writer);
            return writer.write ();
        };
    }

    private static Move endProductionMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.END_PRODUCTION);
            return writer.write ();
        };
    }

    private static Move buyCardMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
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

    private static Move extraBoardProductionMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.EXTRA_PRODUCTION);
            IntegerRequest numExtraPower = new IntegerRequest ("Choose the number of the extra production power from those that you have active", "numExtraPower");
            writer = numExtraPower.handleInput (interlocutor, interpreter, writer);
            ResourceRequest resource = new ResourceRequest ("Choose the resource: digit the type between " + PlayState.getAllResourceTypes () + " and the a amount to move", "resourceProduced");
            writer = resource.handleInput (interlocutor, interpreter, writer);
            return payments (interlocutor, interpreter, writer, "fromWhere").write ();
        };
    }

    private static Move boardProductionMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.BOARD_PRODUCTION);
            ResourceRequest resource = new ResourceRequest ("Choose the resource: digit the type between " + PlayState.getAllResourceTypes () + " and the a amount to move", "produced");
            writer = resource.handleInput (interlocutor, interpreter, writer);
            return payments (interlocutor, interpreter, writer, "payActions").write ();
        };
    }

    private static Move cardProductionMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            MessageWriter writer = new MessageWriter ();
            writer.setHeader (Header.ToServer.PRODUCTION_CARD);
            IntegerRequest numSlot = new IntegerRequest ("Choose the number of slot with the card that you want to use for production", "numSlot");
            writer = numSlot.handleInput (interlocutor, interpreter, writer);
            return payments (interlocutor, interpreter, writer, "payActions").write ();
        };
    }

    private static Move chooseTurnTypeMove() {
        return (ui) -> {
            Interlocutor interlocutor = ui.getInterlocutor();
            Interpreter interpreter = ui.getInterpreter();
            interlocutor.write ("You can choose from\n" + PlayState.TurnType.getPossibilities ());
            String turnType = interpreter.listen ();
            try {
                PlayState.addAvailableMoves (PlayState.TurnType.get (turnType));
            } catch (UnavailableMoveName unavailableMoveName) {
                chooseTurnTypeMove ().ask (ui);
            }
            return null;
        };
    }

    private static MessageWriter payments(Interlocutor interlocutor, Interpreter interpreter, MessageWriter writer, String nameProperty) throws IllegalInputException {
        String addOrStop;
        do {
            PaymentRequest payment = new PaymentRequest ("", nameProperty);
            writer = payment.handleInput (interlocutor, interpreter, writer);
            interlocutor.write ("Digit \"A\" to add another payment, \"S\" to stop");
            addOrStop = interpreter.listen ();
        } while (addOrStop.equals ("A"));
        return writer;
    }
}
