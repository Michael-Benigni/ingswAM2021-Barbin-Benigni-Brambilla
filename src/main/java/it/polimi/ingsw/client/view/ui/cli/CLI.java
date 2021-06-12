package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.lightweightmodel.*;
import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.states.ClientState;
import it.polimi.ingsw.client.view.states.WaitingRoomState;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;
import it.polimi.ingsw.utils.config.StringParser;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.stream.Collectors;

public class CLI implements UI {
    private final int MAX_HORIZ_DIVISIONS = 4;
    private final Interpreter interpreter;
    private final Interlocutor interlocutor;
    private Queue<Sendable> messages;
    private ClientState state;
    private View view;
    private final int WIDTH_SECTION = 160;

    public CLI() {
        this.messages = new ArrayDeque<> ();
        this.state = new WaitingRoomState ();
        interpreter = new Interpreter ();
        interlocutor = new Interlocutor();
    }

    @Override
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
        } catch (IllegalInputException e) {
            userInteraction ();
        }
    }

    @Override
    public void showPersonalBoard() {
        String boardAsString =
                strongboxSection ()
                + "\n\n" + warehouseSection ()
                + "\n\n" + devCardsSection()
                + "\n\n" + inactiveLeaderCardsSection()
                + "\n\n" + activeLeaderCardsSection ()
                + "\n\n" + tempContainerSection ();
        interlocutor.write (boardAsString);
    }

    private String tempContainerSection() {
        LWPersonalBoard board = getView ().getModel ().getPersonalBoard ();
        String strongboxAsString = board.getTemporaryContainer ().getStorableResources ()
                .stream ()
                .map ((resource) -> padding (
                        resource.getResourceType () +
                                " " +
                                resource.getAmount (),
                        " ", WIDTH_SECTION) + "\n")
                .collect(Collectors.joining());
        strongboxAsString += padding (board.getTemporaryContainer ().getEmptyResources () > 0 ? board.getTemporaryContainer ().getEmptyResources () + " White Marbles\n" : "\n", " ", WIDTH_SECTION);
        String sectionHeader = getSectionHeader(" TEMPORARY CONTAINER ");
        return sectionHeader + strongboxAsString;
    }

    private String inactiveLeaderCardsSection() {
        String inactiveLeaderCardsAsString = "";
        ArrayList<LWLeaderCard> cards = getView ().getModel ().getPersonalBoard ().getLeaderCardsNotPlayed ();
        inactiveLeaderCardsAsString = padding (juxtapose (cards.stream ()
                .map ((card) -> encapsulate (card.getDescription(), (int) Math.floor (WIDTH_SECTION / MAX_HORIZ_DIVISIONS - 4)))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll), WIDTH_SECTION / MAX_HORIZ_DIVISIONS - 2), " ", WIDTH_SECTION);
        String sectionHeader = getSectionHeader(" INACTIVE LEADER CARDS ");
        return sectionHeader + inactiveLeaderCardsAsString;
    }

    private String activeLeaderCardsSection() {
        ArrayList<LWLeaderCard> cards = getView ().getModel ().getPersonalBoard ().getLeaderCardsPlayed ();
        String activeLeaderCardsAsString = padding (juxtapose (cards.stream ()
                .map ((card) -> encapsulate (card.getDescription(), (int) Math.floor (WIDTH_SECTION / MAX_HORIZ_DIVISIONS - 2)))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll), WIDTH_SECTION / MAX_HORIZ_DIVISIONS - 1), " ", WIDTH_SECTION);
        String sectionHeader = getSectionHeader(" ACTIVE LEADER CARDS ");
        return sectionHeader + activeLeaderCardsAsString;
    }

    private String devCardsSection() {
        StringBuilder devCardsAsString = new StringBuilder ();
        ArrayList<ArrayList<LWDevCard>> slots = getView ().getModel ().getPersonalBoard ().getSlots ();
        for (ArrayList<LWDevCard> slot : slots) {
            if (!slot.isEmpty ()) {
                devCardsAsString.append (("SLOT n° "))
                        .append (slots.indexOf (slot))
                        .append ("\n")
                        .append (padding (juxtapose (slot.stream ()
                                .map ((card) -> card.getColour ().escape ()
                                        + encapsulate (card.getDescription (), (int) Math.floor (WIDTH_SECTION / MAX_HORIZ_DIVISIONS - 2))
                                        + Colour.RESET.escape ())
                                .collect (ArrayList::new, ArrayList::add, ArrayList::addAll), WIDTH_SECTION / MAX_HORIZ_DIVISIONS - 1), " ", WIDTH_SECTION))
                        .append ("\n");
            }
        }
        String sectionHeader = getSectionHeader(" TOP DEVELOPMENT CARDS ");
        return sectionHeader + devCardsAsString;
    }

    private String strongboxSection() {
        LWPersonalBoard board = getView ().getModel ().getPersonalBoard ();
        String strongboxAsString = board.getStrongbox ()
                .stream ()
                .map ((resource) -> padding (
                        resource.getResourceType () +
                        " " +
                        resource.getAmount (),
                        " ", WIDTH_SECTION) + "\n")
                .collect(Collectors.joining());
        String sectionHeader = getSectionHeader(" STRONGBOX ");
        return sectionHeader + strongboxAsString;
    }

    private String warehouseSection() {
        String warehouseAsString = getView ().getModel ().getPersonalBoard ().getWarehouse ()
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
                                "\n", WIDTH_SECTION / MAX_HORIZ_DIVISIONS * depot.getCapacity ()), " ", WIDTH_SECTION)
                        ).collect(Collectors.joining());
        String sectionHeader = getSectionHeader(" WAREHOUSE ");
        return sectionHeader + warehouseAsString;
    }

    private String getSectionHeader(String header) {
        StringBuilder upperBorder = new StringBuilder ();
        for (int i = 0; i < WIDTH_SECTION; i++)
            upperBorder.append ("*");
        return upperBorder + "\n" + padding (header, "*", WIDTH_SECTION) + "\n";
    }

    private String juxtapose(ArrayList<String> elements, int widthEachElem) {
        ArrayList<ArrayList<String>> allLines = new ArrayList<> ();
        StringParser parser = new StringParser ("\n");
        int maxLines = 0;
        for (String element : elements) {
            ArrayList<String> lines = parser.decompose(element);
            allLines.add (lines);
            if (lines.size () > maxLines)
                maxLines = lines.size ();
        }
        StringBuilder result = new StringBuilder ();
        for (int index = 0; index < maxLines; index++) {
            for (ArrayList<String> strings : allLines) {
                try {
                    result.append (padding (strings.get (index), " ", Math.round (widthEachElem)));
                } catch (IndexOutOfBoundsException e) {
                    result.append (padding ("", " ", Math.round (widthEachElem)));
                }
                // margin
                result.append (" ");
            }
            result/*.append (padding ("", " ", widthEachElem))*/.append ("\n");
        }
        return result.toString ();//widthCheck(result.toString (), allLines.get (0).get (0).length () );
    }

    private ArrayList<ArrayList<String>> cut(ArrayList<String> lines, int widthEachElement) {
        int limit = 0;
        int newLines = 0;
        if (!lines.isEmpty ()) {
            newLines = Math.floorDiv (lines.get (0).length (),  WIDTH_SECTION);
            if (lines.get (0).length () % WIDTH_SECTION > 0)
                newLines++;
            limit = ((WIDTH_SECTION - (WIDTH_SECTION % widthEachElement)) / widthEachElement) * widthEachElement;
        }
        ArrayList<ArrayList<String>> text = new ArrayList<> ();
        for (String line : lines) {
            for (int i = 0; i < newLines; i++) {
                text.add (new ArrayList<> ());
                if (line.length () > WIDTH_SECTION) {
                    String firstLine = line.substring (i * limit, (Math.min ((i + 1) * limit, line.length ())));
                    text.get (i).add (firstLine);
                }
                else
                    text.get (i).add (line);
            }
        }
        return text;
    }

    private String widthCheck(String toString, int widthEachElement) {
        StringParser parser = new StringParser ("\n");
        ArrayList<String> lines = parser.decompose(toString);
        StringBuilder result = new StringBuilder ();
        ArrayList<ArrayList<String>> text = cut (lines, widthEachElement);
        for (ArrayList<String> strings : text) {
            for (String string : strings)
                result.append (string).append ("\n");
            result.append ("\n");
        }
        return result.toString ();
    }

    private String encapsulate(String text, int dimension) {
        StringBuilder horizontalEdge = new StringBuilder();
        for (int i = 0; i < dimension; i++)
            horizontalEdge.append ("_");
        StringParser parser = new StringParser ("\n");
        ArrayList<String> strings = parser.decompose (text);
        int maxLength = StringParser.getMaxLength (strings);
        int finalDimension = Math.max (dimension, maxLength);
        String encapsulation = strings.stream()
                .map ((string)-> "|" + padding (string, " ", finalDimension) + "|\n")
                .collect(Collectors.joining());
        return "_" + horizontalEdge + "_\n" + encapsulation + "|" + horizontalEdge + "|\n";
    }

    private String padding(String toPadding, String charPadding, int dimension) {
        if (toPadding.contains ("\n")) {
            ArrayList<String> lines = new StringParser ("\n").decompose (toPadding);
            return lines.stream ().map ((line) -> padding (line, " ", dimension) + "\n").collect (Collectors.joining ());
        }
        if (toPadding.length () % 2 != 0)
            toPadding += " ";
        while (toPadding.length () < dimension) {
            toPadding = charPadding + toPadding + charPadding;
        }
        return toPadding;
    }

    @Override
    public void showGameBoard() {
        String boardAsString =
                cardsGridSection ()
                        + "\n\n" + faithTrackSection ()
                        + "\n\n" + marketSection();
        interlocutor.write (boardAsString);
    }

    private String marketSection() {
        StringBuilder marketAsString = new StringBuilder ();
        ArrayList<ArrayList<Colour>> marbles = getView ().getModel ().getBoard ().getMarket ().getMarbles ();
        ArrayList<String> headerColumns = new ArrayList<> ();
        int marbleDim = 1;
        /*
        String res = "";
        for (ArrayList<Colour> c : marbles) {
            res += String.format ("ROW: %d\t", marbles.indexOf (c));
            for (Colour co : c) {
                res += String.format ("%s\u2B24\t", co.escape ());
            }
            res += "\n";
            res = padding (res, " ", WIDTH_SECTION);
        }

        System.out.printf ("%s", res);

         */

        for (int column = 0; column < marbles.get (0).size (); column++)
            headerColumns.add (padding (column + "\t", " ", marbleDim));
        marketAsString.append (padding (juxtapose (headerColumns, marbleDim), " ", WIDTH_SECTION));
        marketAsString.append ("\n");
        for (ArrayList<Colour> row : marbles) {
            ArrayList<String> rowStr = row.stream ().map ((marble) ->  "\t" + (colour (marble, padding ("\u2B24", " ", marbleDim))))
                    .collect (ArrayList::new, ArrayList::add, ArrayList::addAll);
            rowStr.add (0, padding ("ROW: " + marbles.indexOf (row) , " ", marbleDim));
            marketAsString.append (padding (juxtapose (rowStr, marbleDim), " ", WIDTH_SECTION));
            marketAsString.append ("\n");
        }
        marketAsString.append (padding ("On Slide: " + colour (getView ().getModel ().getBoard ().getMarket ().getMarbleOnSlide (), "\u2B24"), " ", WIDTH_SECTION));
        marketAsString.append ("\n");
        return getSectionHeader (" MARKET TRAY ") + marketAsString;
    }

    private String faithTrackSection() {
        String faithTrackAsString = "";
        int cellDim = 10;
        ArrayList<LWCell> cells = getView ().getModel ().getBoard ().getFaithTrack ();
        ArrayList<String> cellsAsString = cells.stream ()
                .map ((cell)-> /*colour (cell.isPopeSpace () ? Colour.RED : cell.getVictoryPoints () > 0 ? Colour.YELLOW : Colour.RESET,
                        */encapsulate ("Cell n°" + cells.indexOf (cell) +
                    "\nVP: " + cell.getVictoryPoints () + "\n"
                    + (cell.isPopeSpace () ? "Pope Cell" : " ") + "\n", cellDim)/*)*/).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        faithTrackAsString += juxtapose (cellsAsString, cellDim);
        return getSectionHeader (" FAITH TRACK ") + faithTrackAsString;
    }

    private String cardsGridSection() {
        StringBuilder cardsGridAsString = new StringBuilder ();
        int cardDim = 10;
        ArrayList<String> headerColumns = new ArrayList<> ();
        ArrayList<ArrayList<LWDevCard>> grid = getView ().getModel ().getBoard ().getGrid ().getCardsGrid ();
        for (int column = 0; column < grid.get (0).size (); column++)
            headerColumns.add (padding ("\nCOL: " + column + "\n", " ", cardDim));
        cardsGridAsString.append (padding (juxtapose (headerColumns, cardDim), " ", WIDTH_SECTION));
        cardsGridAsString.append ("\n");
        for (ArrayList<LWDevCard> cardsRow : grid) {
            ArrayList<String> rowStr = cardsRow.stream ().map ((card) -> colour (card.getColour (),
                    encapsulate ((card.getLevel () != 0 ? "\nLV. " + card.getLevel () : "EMPTY"), cardDim)))
                    .collect (ArrayList::new, ArrayList::add, ArrayList::addAll);
            rowStr.add (0, padding ("\n \nROW: " + grid.indexOf (cardsRow) + " \n \n", " ", cardDim));
            cardsGridAsString.append (padding (juxtapose (rowStr, cardDim), " ", WIDTH_SECTION));
            cardsGridAsString.append ("\n");
        }
        return getSectionHeader (" DEVELOPMENT CARDS GRID ") + padding (cardsGridAsString.toString (), " ", WIDTH_SECTION);
    }

    private String colour(Colour colour, String target) {
        StringParser parser = new StringParser ("\n");
        ArrayList<String> lines = parser.decompose(target);
        StringBuilder coloured = new StringBuilder ();
        for (String line : lines) {
            if (colour == null)
                colour = Colour.RESET;
            coloured.append (String.format ("%s%s%s", colour.escape (), line, Colour.RESET.escape ())).append ("\n");
        }
        return coloured.toString ();
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
            actuateMove (usernameMove ());
            actuateMove (newOrExistentMatchMove());
        } catch (Exception e) {
            notifyError (e.getMessage ());
            registration ();
        }
    }

    private Move newOrExistentMatchMove() {
        return (ui) -> {
            ui.getInterlocutor ().write ("In which room you want to be added ? \"FIRST FREE\", \"EXISTENT\", \"NEW\"");
            String whichRoomRequest = ui.getInterpreter ().listen ();
            return getMessageForRoomType(whichRoomRequest);
        };
    }

    private Sendable getMessageForRoomType(String whichRoomRequest) throws IllegalInputException {
        MessageWriter writer = new MessageWriter ();
        switch (whichRoomRequest) {
            case "NEW": {
                writer.setHeader (Header.ToServer.NEW_ROOM);
                break;
            }
            case "EXISTENT": {
                writer.setHeader (Header.ToServer.EXISTING_ROOM);
                IntegerRequest roomIDRequest = new IntegerRequest ("Digit the ID of the room you want to register: ", "ID");
                writer = roomIDRequest.handleInput (interlocutor, interpreter, writer);
                break;
            }
            case "FIRST FREE": {
                writer.setHeader (Header.ToServer.NEW_USER);
                break;
            }
            default:
                throw new IllegalInputException();
        }
        return writer.write ();
    }


    private Move usernameMove() {
        return (ui) -> {
            StringRequest usernameReq = new StringRequest("Set your username (if you want to reconnect to an existing game you must set the same username you have used before disconnection): ", "username");
            MessageWriter writer = usernameReq.handleInput (interlocutor, interpreter, new MessageWriter ());
            writer.setHeader (Header.ToServer.SET_USERNAME);
            return writer.write ();
        };
    }

    private void clear() {
        for (int i = 0; i < 20; i++) {
            interlocutor.write ("\n");
        }
    }


    @Override
    public void notifyError(String info) {
        this.interlocutor.write ("Error: " + info);
        this.nextInputRequest ();
    }

    @Override
    public void notifyMessage(String info) {
        this.interlocutor.write ("From Server: " + info);
    }

    @Override
    public void nextInputRequest() {
        printMenu ();
        this.interlocutor.write ("Digit a new command: ");
    }

    @Override
    public Interlocutor getInterlocutor() {
        return interlocutor;
    }

    @Override
    public Interpreter getInterpreter() {
        return interpreter;
    }

    @Override
    public void setNextState() {
        this.state = this.state.getNextState ();
        clear ();
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

    protected synchronized ClientState getState() {
        return state;
    }

    protected synchronized void addMessage(Sendable sendable) {
        this.messages.add (sendable);
        this.notifyAll ();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public View getView() {
        return view;
    }
}
