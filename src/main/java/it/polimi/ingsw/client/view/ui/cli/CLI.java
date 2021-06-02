package it.polimi.ingsw.client.view.ui.cli;

import it.polimi.ingsw.client.view.lightweightmodel.LWeightPersonalBoard;
import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;
import it.polimi.ingsw.utils.network.Sendable;
import javafx.stage.Stage;

public class CLI extends UI {
    private final Interpreter interpreter;
    private final Interlocutor interlocutor;

    public CLI() {
        super();
        interpreter = new Interpreter ();
        interlocutor = new Interlocutor();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }

    @Override
    public void start() {
        new Thread (() -> {
            registration();
            while (true) {
                Move move = interpreter.listenForMove ();
                actuateMove (move);
                nextInputRequest ();
            }
        }).start ();
    }

    @Override
    public void showPersonalBoard() {
        LWeightPersonalBoard personalBoard = getView ().getModel ().getPersonalBoard ();
        interlocutor.write (personalBoard.toString ());
    }

    @Override
    public void showGameBoard() {

    }

    @Override
    public void showInfoGame() {

    }

    private void actuateMove(Move move) {
        Sendable message = move.ask (this);
        if (message != null) {
            addMessage (message);
        }
    }

    private void printMenu() {
        interlocutor.write (getState ().menu ());
    }

    private void registration() {
        actuateMove (usernameMove ());
        actuateMove (newOrExistentMatchMove());
    }

    private Move newOrExistentMatchMove() {
        return (ui) -> {
            ui.getInterlocutor ().write ("In which room you want to be added ? \"FIRST FREE\", \"EXISTENT\", \"NEW\"");
            String whichRoomRequest = ui.getInterpreter ().listen ();
            return getMessageForRoomType(whichRoomRequest);
        };
    }

    private Sendable getMessageForRoomType(String whichRoomRequest) {
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
                return null;
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
        super.setNextState ();
        clear ();
    }
}
