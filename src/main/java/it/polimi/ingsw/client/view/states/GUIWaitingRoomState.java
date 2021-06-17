package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.moves.WaitingRoomMove;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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

    public GUIWaitingRoomState(GUI gui) {
        super(gui);
    }

    @Override
    public ClientState getNextState() {
        return new GUIPlayState();
    }

    @Override
    public Scene buildScene(GUI gui){
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
            gui.getInterpreter ().addInteraction (a);
            new MoveService (WaitingRoomMove.SET_USERNAME.getMove (), gui).start ();
        });
        grid.add (button, 2,3);

        return scene;
    }

    class MoveService extends Service<Move> {
        private final Move move;
        private final GUI gui;

        MoveService(Move move, GUI gui) {
            this.move = move;
            this.gui = gui;
        }

        @Override
        protected Task<Move> createTask() {
            return new Task<Move> () {
                @Override
                protected Move call() throws Exception {
                    gui.addMessage (move.ask (gui));
                    return move;
                }
            };
        }
    }
}
