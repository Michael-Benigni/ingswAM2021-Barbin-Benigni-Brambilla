package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.lightweightmodel.*;
import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.moves.WaitingRoomMove;
import it.polimi.ingsw.client.view.ui.cli.states.ClientState;
import it.polimi.ingsw.client.view.ui.cli.states.WaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.Sendable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static it.polimi.ingsw.client.view.ui.cli.CLIInterlocutor.*;

public class CLI implements UI {
    private final CLIInterpreter interpreter;
    private final CLIInterlocutor interlocutor;
    private final ArrayDeque<Sendable> messages;
    private ClientState state;
    private Controller controller;

    public CLI() {
        this.messages = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
        this.interpreter = new CLIInterpreter ();
        this.interlocutor = new CLIInterlocutor ();
    }

    public void start() {
        new Thread (() -> {
            registration();
            while (true) {
                userInteraction();
            }
        }).start ();
    }

    private void userInteraction() {
        Move move = interpreter.listenForMove ();
        try {
            actuateMove (move);
            nextInputRequest ();
        } catch (IllegalInputException e) {
            userInteraction ();
        }
    }

    @Override
    public void showGameBoard() {
        String boardAsString = cardsGridSection ()
                + "\n\n" + faithTrackSection ()
                + "\n\n" + marketSection();
        interlocutor.write (boardAsString);
    }

    @Override
    public void showPersonalBoard() {
        String boardAsString =
                strongboxSection ()
                + "\n\n" + warehouseSection ()
                + "\n\n" + devCardsSection()
                + "\n\n" + leaderCardSection()
                + "\n\n" + tempContainerSection ();
        interlocutor.write (boardAsString);
    }

    private String leaderCardSection() {
        return getSectionHeader (" LEADER CARD ", "*") + "\n" + inactiveLeaderCardsSection()
                + "\n\n" + activeLeaderCardsSection ();
    }

    private String tempContainerSection() {
        LWPersonalBoard board = getController ().getModel ().getPersonalBoard ();
        String strongboxAsString = board.getTemporaryContainer ().getStorableResources ()
                .stream ()
                .map ((resource) -> padding (
                        resource.getResourceType () +
                                " " +
                                resource.getAmount (),
                        " ", getWidthSection()) + "\n")
                .collect(Collectors.joining());
        strongboxAsString += padding (board.getTemporaryContainer ().getEmptyResources () > 0 ? board.getTemporaryContainer ().getEmptyResources () + " White Marbles\n" : "\n", " ", getInterlocutor().getWidthSection());
        String sectionHeader = getSectionHeader(" TEMPORARY CONTAINER ", "*");
        return sectionHeader + strongboxAsString;
    }

    private String inactiveLeaderCardsSection() {
        StringBuilder inactiveLeaderCardsAsString;
        ArrayList<LWLeaderCard> cards = getController ().getModel ().getPersonalBoard ().getLeaderCardsNotPlayed ();
        inactiveLeaderCardsAsString = new StringBuilder (padding (juxtapose (cards.stream ()
                .map ((card) -> String.format ("index: %d", card.getSlotIndex ()) + "\n" + encapsulate (card.getDescription (), (int) Math.floor (getInterlocutor().getWidthSection() / getInterlocutor().getMaxHorizDivisions() - 4)))
                .collect (ArrayList::new, ArrayList::add, ArrayList::addAll), getWidthSection() / getMaxHorizDivisions() - 2), " ", getInterlocutor().getWidthSection()));
        String sectionHeader = getSectionHeader(" INACTIVE LEADER CARDS ", ".");
        return sectionHeader + "\n" + inactiveLeaderCardsAsString;
    }

    private String activeLeaderCardsSection() {
        ArrayList<LWLeaderCard> cards = getController ().getModel ().getPersonalBoard ().getLeaderCardsPlayed ();
        StringBuilder activeLeaderCardsAsString = new StringBuilder (padding (juxtapose (cards.stream ()
                .map ((card) -> String.format ("index: %d", card.getSlotIndex ()) + "\n" + encapsulate (card.getDescription (), (int) Math.floor (getInterlocutor().getWidthSection() / getInterlocutor().getMaxHorizDivisions() - 2)))
                .collect (ArrayList::new, ArrayList::add, ArrayList::addAll), getWidthSection() / getMaxHorizDivisions() - 1), " ", getInterlocutor().getWidthSection()));
        String sectionHeader = getSectionHeader(" ACTIVE LEADER CARDS ", ".");
        activeLeaderCardsAsString.append ("\n");
        return sectionHeader + "\n" + activeLeaderCardsAsString + "\n" + activeEffectSection();
    }

    private String activeEffectSection() {
        LWPersonalBoard board = getController ().getModel ().getPersonalBoard ();
        String effects = board.getExtraProductionPowers ()
                .stream ()
                .map ((power) -> padding (
                        "Index of power: " + power.getIndexOfPower ()
                                + "\nNum. resources to pay: " + power.getNumberOfResourceToPay ()
                                + "\nNum. resources to pay: " + power.getNumberOfResourceToProduce ()
                                + "\nFixed resource you have to pay: " + power.getConsumedResource ().getAmount () + power.getConsumedResource ().getResourceType () + "\n\n",
                        " ", getWidthSection()) + "\n")
                .collect(Collectors.joining());
        effects += "\n\n" + board.getWhiteMarblePowers ()
                .stream ()
                .map ((power) -> padding (
                        "Index of power: " + power.getPowerWMIndex ()
                                + "\nResource you'll obtain: " + power.getResourceObtained ().getAmount () + power.getResourceObtained ().getResourceType () + "\n\n",
                        " ", getWidthSection()) + "\n")
                .collect(Collectors.joining());
        String sectionHeader = padding (" ACTIVE EFFECTS ", "_", getWidthSection ());
        return sectionHeader + effects;
    }

    private String devCardsSection() {
        StringBuilder devCardsAsString = new StringBuilder ();
        ArrayList<ArrayList<LWDevCard>> slots = getController ().getModel ().getPersonalBoard ().getSlots ();
        int slotIdx = 0;
        for (ArrayList<LWDevCard> slot : slots) {
            devCardsAsString.append (padding ("SLOT n° ", ".", getWidthSection ()))
                    .append (slotIdx)
                    .append ("\n");
            slotIdx++;
            if (!slot.isEmpty ()) {
                devCardsAsString.append ("\n");
                devCardsAsString.append (padding (juxtapose (slot.stream ()
                        .map ((card) -> (slot.size () == card.getIndexInSlot () - 1 ? "TOP CARD" : " ") + "\n"
                                + encapsulate (card.getDescription (), (int) Math.floor (getWidthSection() / getInterlocutor().getMaxHorizDivisions() - 2)))
                        .collect (ArrayList::new, ArrayList::add, ArrayList::addAll), getWidthSection() / getInterlocutor().getMaxHorizDivisions() - 1), " ", getInterlocutor().getWidthSection()))
                        .append ("\n");
            }
        }
        String sectionHeader = getSectionHeader(" SLOT DEVELOPMENT CARDS ", "*");
        return sectionHeader + devCardsAsString;
    }

    private String strongboxSection() {
        LWPersonalBoard board = getController ().getModel ().getPersonalBoard ();
        String strongboxAsString = board.getStrongbox ()
                .stream ()
                .map ((resource) -> padding (
                        resource.getResourceType () +
                        " " +
                        resource.getAmount (),
                        " ", getWidthSection()) + "\n")
                .collect(Collectors.joining());
        String sectionHeader = getSectionHeader(" STRONGBOX ", "*");
        return sectionHeader + strongboxAsString;
    }

    private String warehouseSection() {
        String warehouseAsString = getController ().getModel ().getPersonalBoard ().getWarehouse ()
                .stream ()
                .map ((depot) -> //TODO: add symbols to each type
                                padding (encapsulate ("content: " +
                                (depot.getStoredResource () != null ? depot.getStoredResource ().getResourceType () : "") +
                                " " +
                                (depot.getStoredResource () != null ? depot.getStoredResource ().getAmount () : "") +
                                "\n" +
                                "capacity: " +
                                depot.getCapacity () +
                                "\n" +
                                ((depot.getType () != null) ? "type: " : "") +
                                ((depot.getType () != null) ? depot.getType () : "") +
                                "\n", getWidthSection() / getMaxHorizDivisions() * depot.getCapacity ()), " ", getInterlocutor().getWidthSection())
                        ).collect(Collectors.joining());
        String sectionHeader = getSectionHeader(" WAREHOUSE ", "*");
        return sectionHeader + warehouseAsString;
    }

    private String marketSection() {
        StringBuilder marketAsString = new StringBuilder ();
        final String LEFT_ARROW = "\uD83E\uDC14\n";
        final String UP_ARROW = "\uD83E\uDC15";
        final String MARBLE = "\u2B24";
        ArrayList<ArrayList<Colour>> marbles = getController ().getModel ().getBoard ().getMarket ().getMarbles ();
        String headerColumns = "";
        StringBuilder res = new StringBuilder ();
        for (ArrayList<Colour> c : marbles) {
            res.append (String.format ("ROW: %d\t", marbles.indexOf (c)));
            for (Colour co : c)
                res.append (String.format ("%s\t", colour (co, MARBLE)));
            res.append (LEFT_ARROW);
        }
        for (int column = 0; column < marbles.get (0).size (); column++)
            headerColumns = String.format ("%s%s\t", headerColumns, column);
        marketAsString.append (String.format ("\t\t%s\n", headerColumns));
        marketAsString.append (res);
        StringBuilder arrows = new StringBuilder ("\t");
        for (int column = 0; column < marbles.get (0).size (); column++)
            arrows.append (String.format ("\t%s", UP_ARROW));
        marketAsString.append (String.format ("%s\n", arrows));
        marketAsString.append (String.format ("On Slide: %s", colour (getController ().getModel ().getBoard ().getMarket ().getMarbleOnSlide (),"\u2B24")));
        marketAsString.append ("\n");
        return getSectionHeader (" MARKET TRAY ", "*") + marketAsString;
    }

    private String faithTrackSection() {
        String faithTrackAsString = "";
        int cellDim = 12;
        InfoMatch info = getController ().getModel ().getInfoMatch ();
        int numOfPlayers = info.getOtherPlayersUsernames ().size () + 1 ;
        ArrayList<LWCell> cells = getController ().getModel ().getBoard ().getFaithTrack ();
        ArrayList<String> cellsAsString = cells.stream ()
                .map ((cell)-> encapsulate ("Cell n°" + cells.indexOf (cell)
                        + "\nVP: " + cell.getVictoryPoints () + "\n"
                        + cell.getSection () + "\n"
                        + (cell.isPopeSpace () ? "Pope Cell" : " ")
                        + "\n"
                        + String.join ("\n", (cell.getPlayersInThisCell ()))
                        + repeat ("\n ", numOfPlayers - cell.getPlayersInThisCell ().size ()), cellDim))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        faithTrackAsString += (juxtapose (cellsAsString,cellDim + 1 ));
        return getSectionHeader (" FAITH TRACK ", "*") + faithTrackAsString;
    }

    private String cardsGridSection() {
        StringBuilder devCardsAsString = new StringBuilder ();
        LWCardsGrid grid = getController ().getModel ().getBoard ().getGrid ();
        for (int column = 0; column < grid.getColumns(); column++)
            devCardsAsString.append (padding (String.format ("COL: %d", column), " ", getWidthSection() / grid.getColumns()));
        devCardsAsString.append ("\n");
        for (ArrayList<LWDevCard> row : grid.getCardsGrid ()) {
            ArrayList<String> elements = row.stream ()
                    .map ((card) -> encapsulate (card.getDescription () != null ? card.getDescription () : " ", (int) Math.floor (getWidthSection() / getMaxHorizDivisions() - 4)))
                    .collect (ArrayList::new, ArrayList::add, ArrayList::addAll);
            devCardsAsString.append (String.format ("ROW: %d\n", grid.getCardsGrid ().indexOf (row)));
            devCardsAsString.append (padding (juxtapose (elements, getWidthSection() / getMaxHorizDivisions() - 2), " ", getWidthSection()))
                    .append ("\n");
        }
        String sectionHeader = getSectionHeader(" CARDS GRID ", "*");
        return sectionHeader + devCardsAsString;
    }

    @Override
    public void showInfoGame() {

    }

    private void actuateMove(Move move) throws IllegalInputException {
        Sendable message = move.ask (this);
        if (message != null) {
            addMessage (message);
        }
    }

    @Override
    public void printMenu() {
        interlocutor.write (getState ().menu ());
    }

    private void registration() {
        try {
            actuateMove (WaitingRoomMove.SET_USERNAME.getMove ());
            actuateMove (WaitingRoomMove.CHOOSE_ROOM.getMove ());
        } catch (Exception e) {
            notifyError (e.getMessage ());
            registration ();
        }
    }

    private void clear() {
        for (int i = 0; i < 20; i++) {
            interlocutor.write ("\n");
        }
    }


    @Override
    public synchronized void notifyError(String info) {
        this.interlocutor.write (colour (Colour.RED, "Error: " + info));
        this.nextInputRequest ();
    }

    @Override
    public synchronized void notifyMessage(String info) {
        this.interlocutor.write (colour (Colour.BLUE, "From Server: " + info));
    }

    @Override
    public synchronized void nextInputRequest() {
        printMenu ();
        this.interlocutor.write ("Digit a new command: ");
    }

    @Override
    public synchronized CLIInterlocutor getInterlocutor() {
        return interlocutor;
    }

    @Override
    public synchronized CLIInterpreter getInterpreter() {
        return interpreter;
    }

    @Override
    public synchronized void setNextState() {
        this.state = this.state.getNextState ();
        clear ();
    }

    @Override
    public synchronized ClientState getState() {
        return state;
    }

    @Override
    public synchronized void addMessage(Sendable sendable) {
        this.messages.addLast (sendable);
        this.notifyAll ();
    }

    @Override
    public synchronized Sendable getNextMessage() {
        while (this.messages.size () == 0) {
            try {
                wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        return messages.remove ();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
