package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.moves.WaitingRoomMove;
import it.polimi.ingsw.client.view.ui.gui.MoveService;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GUIWaitingRoomState extends GUIState{

    @Override
    public GUIState getNextState() {
        return new GUIPlayState();
    }

    @Override
    public void buildScene(GUI gui){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 200, 200);

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);


        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Button button = new Button ("Set your username");
        button.setOnAction (actionEvent -> {
            String a = userTextField.getText ();
            gui.getInterpreter ().addInteraction ("username", a);
            new MoveService (WaitingRoomMove.SET_USERNAME.getMove (), gui).start ();
        });

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

        Button existentGame = new Button("Join a friend's game");
        existentGame.setOnAction(actionEvent -> {
            gui.getInterpreter ().addInteraction ("room", "EXISTENT");
            new MoveService(WaitingRoomMove.CHOOSE_ROOM.getMove(), gui).start();
        });

        Label existentMatch = new Label("Insert a match ID to join " +
                "\nyour friends party! :D");
        grid.add(existentMatch, 0, 2);

        TextField matchIDTextField = new TextField();
        grid.add(matchIDTextField, 1, 2);

        grid.add (button, 2,1);
        grid.add(firstFree, 3, 5);
        grid.add(newRoom, 3, 6);
        grid.add(existentGame, 3, 7);

        setScene (scene);


    }
}
