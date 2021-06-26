package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import it.polimi.ingsw.client.view.ui.gui.JsonImageLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PersonalboardTab extends Tab {

    public PersonalboardTab(){
        super("Personal Board");
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ImageView imageView = new ImageView (loader.loadPersonalBoardImage ());
        imageView.setPreserveRatio (true);
        HBox hBox = new HBox ();
        hBox.getChildren ().add (imageView);
        hBox.setBackground (new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        imageView.fitHeightProperty().bind (JavaFXApp.getFixedHeight ().multiply (0.93));
        this.setClosable (false);
        this.setContent(hBox);
    }
}
