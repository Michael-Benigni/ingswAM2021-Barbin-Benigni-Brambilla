package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.lightweightmodel.InfoMatch;
import it.polimi.ingsw.client.view.moves.WaitingRoomMove;
import it.polimi.ingsw.client.view.ui.gui.MoveService;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import it.polimi.ingsw.client.view.ui.gui.Popup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GUIWaitingRoomState extends GUIState {

    private static GUIWaitingRoomState instance;
    private Button startButton;
    private static Scene scene;
    private static Scene startMatchScene;
    private static Scene waitScene;
    private Label labelNumPlayersInWait;
    private Label labelNumRoom;
    private Label labelSettedNewSize;

    private GUIWaitingRoomState() {
        instance = this;
    }

    public static Scene getStartMatchScene(boolean isLeader) {
        return isLeader ? startMatchScene : waitScene;
    }

    @Override
    protected void setSceneInstance(Scene scene) {
        GUIWaitingRoomState.scene = scene;
    }

    @Override
    public GUIState getNextState() {
        return GUIPlayState.getInstance ();
    }

    @Override
    public Scene buildScene(GUI gui){

        if(getSceneInstance () == null) {

            startMatchScene = goToSetPlayersScene(gui);
            waitScene = goToWaitScene(gui);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Scene scene = new Scene(grid, 700, 400);


            Text scenetitle = new Text("Welcome");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 30));

            Label username = new Label("Username:");

            TextField userTextField = new TextField();

            Label insertMatchID = new Label("Insert a match ID " +
                    "\nto join a friend's party!");

            TextField matchIDTextField = new TextField();

            Button firstFree = new Button ("Automatic matchmaking");
            firstFree.setOnAction(actionEvent -> {
                gui.getInterpreter ().addInteraction ("room", "FIRST FREE");
                new MoveService(WaitingRoomMove.CHOOSE_ROOM.getMove(), gui).start();
            });

            Button newRoom = new Button ("Create a new game");
            newRoom.setOnAction(actionEvent -> {
                gui.getInterpreter ().addInteraction ("room", "NEW");
                new MoveService(WaitingRoomMove.CHOOSE_ROOM.getMove(), gui).start();
            });

            Button existentGame = new Button("Join a friend's party");
            existentGame.setOnAction(actionEvent -> {
                String idFriendParty = matchIDTextField.getText();
                if(idFriendParty.equals("")){
                    Popup.alert("Error", "You must insert a valid match ID");
                }
                else{
                    gui.getInterpreter().addInteraction("ID", idFriendParty);
                    gui.getInterpreter ().addInteraction ("room", "EXISTENT");
                    new MoveService(WaitingRoomMove.CHOOSE_ROOM.getMove(), gui).start();
                }
            });

            Button setYourUsername = new Button ("Set your username");
            setYourUsername.setOnAction (actionEvent -> {
                String insertedUsername = userTextField.getText ();
                if(insertedUsername.equals("")){
                    Popup.alert("Error", "You must insert a valid username");
                }
                else{
                    gui.getInterpreter ().addInteraction ("username", insertedUsername);
                    new MoveService (WaitingRoomMove.SET_USERNAME.getMove (), gui).start ();
                    firstFree.setDisable(false);
                    existentGame.setDisable(false);
                    newRoom.setDisable(false);
                    matchIDTextField.setDisable(false);
                }
            });

            grid.add(scenetitle, 0, 0);
            grid.add(username, 0, 1);
            grid.add(userTextField, 1, 1);
            grid.add(insertMatchID, 0, 2);
            grid.add(existentGame, 2, 2);
            grid.add(matchIDTextField, 1, 2);
            grid.add (setYourUsername, 2,1);
            grid.add(firstFree, 1, 5);
            grid.add(newRoom, 1, 6);

            firstFree.setDisable(true);
            existentGame.setDisable(true);
            newRoom.setDisable(true);
            matchIDTextField.setDisable(true);

            setSceneInstance (scene);
        }

        return getSceneInstance ();
    }



    public Scene goToSetPlayersScene(GUI gui){

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(gridPane, 700, 400);

        Text scenetitle = new Text("Match Settings");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 30));

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        choiceBox.getItems().add("1");
        choiceBox.getItems().add("2");
        choiceBox.getItems().add("3");
        choiceBox.getItems().add("4");

        choiceBox.setValue("4");

        InfoMatch infoMatch = gui.getController().getModel().getInfoMatch();

        this.startButton = new Button("START");
        this.startButton.setDisable(true);
        this.startButton.setOnAction (e -> new MoveService (WaitingRoomMove.START_MATCH.getMove (), gui).start ());

        this.startButton.setOnAction(actionEvent -> {
            new MoveService (WaitingRoomMove.START_MATCH.getMove (), gui).start ();
        });

        Button setNumPlayersButton = new Button("Set room size");

        this.labelSettedNewSize = new Label("");

        this.labelNumRoom = new Label("Room N° " + infoMatch.getRoomID());

        this.labelNumPlayersInWait = new Label("0 players entered your room");

        Label infoSelect = new Label("Select the number of players: ");

        setNumPlayersButton.setOnAction(actionEvent -> {
            String numberOfPlayers = choiceBox.getValue();
            gui.getInterpreter ().addInteraction ("dimension", numberOfPlayers);
            new MoveService (WaitingRoomMove.SET_NUM_PLAYERS.getMove (), gui).start ();
        });

        gridPane.add(scenetitle, 0, 0);
        gridPane.add(labelNumRoom, 0, 1);
        gridPane.add(labelNumPlayersInWait, 0, 2);
        gridPane.add(infoSelect, 0, 3);
        gridPane.add(choiceBox, 1, 3);
        gridPane.add(setNumPlayersButton, 2, 3);
        gridPane.add(labelSettedNewSize, 3, 3);
        gridPane.add(this.startButton, 1, 4);


        return scene;
    }

    private Scene goToWaitScene(GUI gui) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(gridPane, 700, 400);

        Text scenetitle = new Text("Waiting Room");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 30));

        Label waitLabel = new Label("Please, wait until the party manager start the game");

        gridPane.add(scenetitle, 0, 0);
        gridPane.add(waitLabel, 0, 2);

        return scene;
    }

    @Override
    protected Scene getSceneInstance() {
        return scene;
    }

    public static GUIWaitingRoomState getInstance(){
        if (instance == null)
            return new GUIWaitingRoomState();
        else
            return instance;
    }

    public void enableButtonStartGame() {
        this.startButton.setDisable(false);
    }

    public void notifyNewPlayerInRoom(GUI gui) {

        //TODO: this method isn't finished, so don't care about it, thank you guys. Ale

        InfoMatch infoMatch = gui.getController().getModel().getInfoMatch();
        ArrayList<String> usernames = infoMatch.getOtherPlayersUsernames();
        this.labelNumPlayersInWait.setText("Players in your party:\n" + usernames.get(0));
    }

    public void updateRoomIDLabelInMatchSettings(GUI gui){
        InfoMatch infoMatch = gui.getController().getModel().getInfoMatch();
        this.labelNumRoom.setText("Room N° " + infoMatch.getRoomID());
    }

    public void notifyNewRoomSize(GUI gui){
        InfoMatch infoMatch = gui.getController().getModel().getInfoMatch();
        this.labelSettedNewSize.setText("The Room size has been setted to " + infoMatch.getWaitingRoomSize());
    }
}
