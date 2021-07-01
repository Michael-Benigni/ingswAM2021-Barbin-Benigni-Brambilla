package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.lightweightmodel.*;
import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.moves.WaitingRoomMove;
import it.polimi.ingsw.client.view.ui.cli.states.ClientState;
import it.polimi.ingsw.client.view.ui.cli.states.WaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.QuitMessage;
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
    private static CLI instance;
    private boolean isRunning;


    private CLI() {
        this.messages = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
        this.interpreter = new CLIInterpreter ();
        this.interlocutor = new CLIInterlocutor ();
        this.isRunning = false;
    }

    public static CLI getInstance() {
        if (instance == null)
            instance = new CLI ();
        return instance;
    }

    @Override
    public void start() {
        new Thread (() -> {
            boolean connectionOk = waitConnection ();
            if (connectionOk) {
                registration ();
                while (isRunning) {
                    userInteraction ();
                }
            }
        }).start ();
    }

    private synchronized boolean waitConnection() {
        while (!isRunning)
            try {
                wait (ClientPrefs.getTimeToWaitConnection());
            } catch (InterruptedException e) {
            }
        return isRunning;
    }

    @Override
    public synchronized void onMarketChanged() {

    }

    @Override
    public synchronized void onFaithTrackChanged() {

    }

    @Override
    public synchronized void onPlayerPositionFaithTrackChanged() {

    }

    @Override
    public synchronized void onCardsGridBuilt() {

    }

    @Override
    public synchronized void onCardBoughtFromGrid() {

    }

    @Override
    public synchronized void onRoomIDChanged() {

    }

    @Override
    public synchronized void onSetUsername() {

    }

    @Override
    public synchronized void onPositionInTurnChanged() {
        controller.getUI().notifyMessage ("You are the " + controller.getModel ().getInfoMatch ().getPlayerPositionInTurn () + "° player!");
    }

    @Override
    public synchronized void onRoomSizeChanged() {
        notifyMessage ("The number of players of the Game has been set to " + controller.getModel ().getInfoMatch ().getWaitingRoomSize ());
    }

    @Override
    public synchronized void onNewPlayerInGame() {

    }

    @Override
    public synchronized void onIsLeaderChanged() {

    }

    @Override
    public void onLeaderDisconnected() {

    }

    @Override
    public void onWarehouseChanged() {

    }

    @Override
    public synchronized void onStrongboxChanged() {

    }

    @Override
    public synchronized void onTempContainerChanged() {

    }

    @Override
    public synchronized void onSlotDevCardsChanged() {

    }

    @Override
    public synchronized void onSlotLeaderCardsChanged() {

    }

    @Override
    public synchronized void onWMPowerChanged() {

    }

    @Override
    public synchronized void onXPowersChanged() {

    }

    @Override
    public synchronized void onCurrentPlayerChanged(String additionalMsg) {
        InfoMatch infoMatch = controller.getModel ().getInfoMatch ();
        String playerPos = infoMatch.getCurrentPlayerPos ();
        String msg;
        if (playerPos.equals (infoMatch.getPlayerPositionInTurn ())) {
            msg = "It's your Turn! ";
            if (additionalMsg != null)
                msg += additionalMsg;
        }
        else
            msg = "It's the turn of " + infoMatch.getPlayerAt (infoMatch.getCurrentPlayerPos()) + " (Player number " + playerPos + ")" + ". Wait your turn.";
        notifyMessage (msg);
    }

    @Override
    public synchronized void onGameOver(ArrayList<String> winnersNames, ArrayList<String> losersNames, ArrayList<Integer> winnersVPs, ArrayList<Integer> losersVPs, String addInfo) {
        String message = "";
        String winners = String.join(", ", winnersNames) + ".";
        String losers = String.join(", ", losersNames) + ".";
        if (winnersNames.size() > 1)
            message += "The winners are " + winners + "\n";
        else
            message += "The winner is " + winners + "\n";
        if (winnersNames.size() > 1)
            message += "The losers are " + losers + "\n\n";
        else
            message += "The loser is " + losers + "\n\n";
        message += "--------------------VPs--------------------\n\n";
        message += winnersNames.stream().map((name) -> name + " -> VP: " + winnersVPs.get(winnersNames.indexOf(name)));
        message += "\n";
        message += losersNames.stream().map((name) -> name + " -> VP: " + losersVPs.get(losersNames.indexOf(name)));
        if (addInfo != null)
            message += addInfo;
        controller.getUI().notifyMessage(message);
    }

    public synchronized void notifyRoomFull() {
        nextInputRequest ();
    }

    @Override
    public void onUserInRoomEnteredOrDisconnected() {
    }

    @Override
    public synchronized void notifyErrorConnection() {
        getInterlocutor ().write ("Connection Refused. The Server is not working.\n Try again in few minutes.");
        if (isRunning)
            getInterlocutor ().write ("Press \"QUIT\" to terminate the app." );
        else
            notifyAll ();
    }

    @Override
    public synchronized void connectionSuccessful() {
        isRunning = true;
        notifyAll ();
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
    public synchronized void showGameBoard() {
        String boardAsString = cardsGridSection ()
                + "\n\n" + faithTrackSection ()
                + "\n\n" + marketSection();
        interlocutor.write (boardAsString);
    }

    @Override
    public synchronized void showPersonalBoard() {
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
            devCardsAsString.append (padding ("SLOT n° " + slotIdx, ".", getWidthSection ()))
                    .append ("\n");
            slotIdx++;
            if (!slot.isEmpty ()) {
                devCardsAsString.append ("\n");
                devCardsAsString.append (padding (juxtapose (slot.stream ()
                        .map ((card) -> (slot.size () - 1 == card.getIndexInSlot () ? "TOP CARD" : " ") + "\n"
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
        final String LEFT_ARROW = "<-\n";//"\uD83E\uDC14\n";
        final String UP_ARROW = "^";//"\uD83E\uDC15";
        final String MARBLE = "O";//"\u2B24";
        ArrayList<ArrayList<Colour>> marbles = getController ().getModel ().getBoard ().getMarket ().getMarbles ();
        String headerColumns = "";
        StringBuilder res = new StringBuilder ();
        for (ArrayList<Colour> c : marbles) {
            res.append (String.format ("ROW: %d\t\t", marbles.indexOf (c)));
            for (Colour co : c)
                res.append (String.format ("%s\t", colour (co, MARBLE)));
            res.append ("\t" + LEFT_ARROW);
        }
        for (int column = 0; column < marbles.get (0).size (); column++)
            headerColumns = String.format ("%s%s\t", headerColumns, column);
        marketAsString.append (String.format ("\t\t%s\n", headerColumns));
        marketAsString.append (res);
        StringBuilder arrows = new StringBuilder ("\n\t");
        for (int column = 0; column < marbles.get (0).size (); column++)
            arrows.append (String.format ("\t%s", UP_ARROW));
        marketAsString.append (String.format ("%s\n", arrows));
        marketAsString.append (String.format ("On Slide: %s", colour (getController ().getModel ().getBoard ().getMarket ().getMarbleOnSlide (),MARBLE)));
        marketAsString.append ("\n");
        return getSectionHeader (" MARKET TRAY ", "*") + marketAsString;
    }

    private String faithTrackSection() {
        String faithTrackAsString = "";
        int cellDim = 12;
        InfoMatch info = getController ().getModel ().getInfoMatch ();
        int numOfPlayers = info.getOtherPlayersUsernames ().size () + 1 ;
        ArrayList<LWCell> positions = getController ().getModel ().getBoard ().getFaithTrack ();
        ArrayList<String> cellsAsString = positions.stream ()
                .map ((cell)-> encapsulate ("Cell n°" + positions.indexOf (cell)
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

    public synchronized void printMenu() {
        interlocutor.write (getCurrentState ().menu ());
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
        nextInputRequest ();
    }

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
    public synchronized ClientState getCurrentState() {
        return state;
    }

    @Override
    public synchronized void addMessage(Sendable sendable) {
        this.messages.addLast (sendable);
        this.notifyAll ();
        if (QuitMessage.isQuitMessage (sendable.transmit ()))
            isRunning = false;
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
    public synchronized void notifyRegistration(boolean isLeader, int orderOfRegistration) {
        InfoMatch infoMatch = controller.getModel ().getInfoMatch ();
        notifyMessage (orderOfRegistration + "° user registered, in the waiting room n° " + infoMatch.getRoomID () + ", with name: " + infoMatch.getYourUsername ());
    }

    @Override
    public synchronized void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public synchronized Controller getController() {
        return controller;
    }
}
