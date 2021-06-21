package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GUIPlayState extends GUIState {

    @Override
    public GUIState getNextState() {
        return new GUIGameOverState();
    }

    @Override
    public void buildScene(GUI gui) {
        Image background = new Image (Objects.requireNonNull (getClass ().getResourceAsStream ("/images/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png")));
        ImageView view = new ImageView (background);
        TabPane tabPane = new TabPane ();
        Tab tabPersonalBoard = new Tab ("Personal Board", view);
        Tab tabGameBoard = new Tab ("Game Board", view);
        tabPane.getTabs ().addAll (tabGameBoard, tabPersonalBoard);
        Scene scene = new Scene (tabPane);
        setScene (scene);
    }
}
