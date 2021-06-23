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

public class GUIWaitingRoomState extends GUIState {

    private static GUIWaitingRoomState instance;
    private Button startButton;
    private static Scene scene;
    private static Scene startMatchScene;
    private static Scene waitScene;

    private GUIWaitingRoomState() {
        instance = this;
    }

    public static Scene getStartMatchScene(boolean isLeader) {
        return isLeader ? startMatchScene : waitScene;
    }

    @Override
    protected void setScene(Scene scene) {
        GUIWaitingRoomState.scene = scene;
    }

    @Override
    public GUIState getNextState() {
        return new GUIPlayState();
    }

    @Override
    public Scene buildScene(GUI gui){

        if(getScene() == null) {

            startMatchScene = goToSetPlayersScene(gui);
            waitScene = goToWaitScene(gui);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Scene scene = new Scene(grid, 700, 400);


            Text scenetitle = new Text("Welcome");
            scenetitle.setFont(Font.font("sans serif", FontWeight.EXTRA_BOLD, 30));

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

            setScene(scene);
        }

        return getScene();
    }



    public Scene goToSetPlayersScene(GUI gui){

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(gridPane, 700, 400);

        Text scenetitle = new Text("Match Settings");
        scenetitle.setFont(Font.font("sans serif", FontWeight.EXTRA_BOLD, 30));

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        choiceBox.getItems().add("1");
        choiceBox.getItems().add("2");
        choiceBox.getItems().add("3");
        choiceBox.getItems().add("4");

        choiceBox.setValue("1");

        InfoMatch infoMatch = gui.getController().getModel().getInfoMatch();

        this.startButton = new Button("START");
        this.startButton.setDisable(true);

        Label infoRoom = new Label("You are in the Room N° " + infoMatch.getRoomID() +
                "\n" + infoMatch.getNumCurrentPlayersInRoom() + "players entered your room");

        this.startButton.setOnAction(actionEvent -> {
            String numberOfPlayers = choiceBox.getValue();
            gui.getInterpreter ().addInteraction ("dimension", numberOfPlayers);
            new MoveService (WaitingRoomMove.SET_NUM_PLAYERS.getMove (), gui).start ();
            new MoveService (WaitingRoomMove.START_MATCH.getMove (), gui).start ();
        });

        gridPane.add(scenetitle, 0, 0);
        gridPane.add(choiceBox, 0, 1);
        gridPane.add(this.startButton, 0, 2);

        return scene;
    }

    private Scene goToWaitScene(GUI gui) {
        return null;
    }

    @Override
    protected Scene getScene() {
        return scene;
    }

    public Button getStartButton() {
        return startButton;
    }

    public static GUIWaitingRoomState getInstance(){
        if (instance == null)
            return new GUIWaitingRoomState();
        else
            return instance;
    }
}
