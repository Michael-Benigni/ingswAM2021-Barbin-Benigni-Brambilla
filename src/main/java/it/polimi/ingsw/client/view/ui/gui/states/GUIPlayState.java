package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class GUIPlayState extends GUIState {

    @Override
    public GUIState getNextState() {
        return new GUIGameOverState();
    }

    @Override
    public void buildScene(GUI gui) {
        Image background = new Image (getClass ().getResourceAsStream ("/images/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png"), 300, 300, true, true);
        ImageView view = new ImageView (background);
        view.setRotate (-90);
        view.fitWidthProperty ();
        view.fitHeightProperty ();
        TabPane tabPane = new TabPane ();
        Tab tabPersonalBoard = getPersonalBoardTab ();
        Tab tabGameBoard = getGameBoardTab();
        tabPane.getTabs ().addAll (tabGameBoard, tabPersonalBoard);
        Scene scene = new Scene (tabPane);
        setScene (scene);
    }

    private Tab getGameBoardTab() {
        Image background = new Image (getClass ().getResourceAsStream ("/images/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png"), 300, 300, true, true);
        ImageView view = new ImageView (background);
        view.setRotate (-90);
        view.fitWidthProperty ();
        view.fitHeightProperty ();
        return new Tab ("Personal Board", view);
    }

    private Tab getPersonalBoardTab() {
        Image background = new Image (getClass ().getResourceAsStream ("/images/punchboard/plancia portabiglie.png"), 300, 300, true, true);
        ImageView view = new ImageView (background);
        view.fitWidthProperty ();
        view.fitHeightProperty ();
        return new Tab ("Game Board", view);
    }
}
