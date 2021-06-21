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
    public void buildScene(GUI gui) {
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

            Button button = new Button ("username");
            button.setOnAction (actionEvent -> {
                String a = userTextField.getText ();
                gui.getInterpreter ().addInteraction ("username", a);
                new MoveService (WaitingRoomMove.SET_USERNAME.getMove (), gui).start ();
            });
            grid.add (button, 2,3);
            setScene (scene);
    }

}
