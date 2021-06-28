package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import it.polimi.ingsw.client.view.ui.gui.JsonImageLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

import java.util.concurrent.TransferQueue;

public class PersonalboardTab extends Tab {

    private StackPane slotsDevCards;
    private BorderPane personalBoard;
    private HBox faithTrack;
    private VBox warehouseAndStrongbox;

    public PersonalboardTab(){
        super("Personal Board");
        warehouseAndStrongbox = new VBox();
        faithTrack = new HBox();
        slotsDevCards = new StackPane();
        personalBoard = new BorderPane();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ImageView imageView = new ImageView (loader.loadPersonalBoardImage ());
        imageView.setPreserveRatio (true);

        Translate translate = new Translate();
        translate.setX(-16);
        Translate translate1 = new Translate();
        translate1.setX(-8);
        Translate translate2 = new Translate();
        translate2.setX(-105);
        translate2.setY(-85);

        GridPane slotsGrid = new GridPane();

        ImageView aaaaaaaa = new ImageView(loader.loadDevCardImage(45));
        aaaaaaaa.fitHeightProperty().bind(JavaFXApp.getFixedHeight ().multiply (0.37));
        aaaaaaaa.setPreserveRatio(true);

        aaaaaaaa.getTransforms().add(translate);

        ImageView bbb = new ImageView(loader.loadDevCardImage(23));
        bbb.fitHeightProperty().bind(JavaFXApp.getFixedHeight ().multiply (0.37));
        bbb.setPreserveRatio(true);

        bbb.getTransforms().add(translate1);


        ImageView ccc = new ImageView(loader.loadDevCardImage(14));
        ccc.fitHeightProperty().bind(JavaFXApp.getFixedHeight ().multiply (0.37));
        ccc.setPreserveRatio(true);
        slotsGrid.add(aaaaaaaa, 0, 0);
        slotsGrid.add(bbb, 1, 0);
        slotsGrid.add(ccc, 2, 0);
        slotsGrid.setAlignment(Pos.BOTTOM_RIGHT);
        slotsGrid.getTransforms().add(translate2);


        slotsDevCards.getChildren().add(slotsGrid);

        personalBoard.getChildren ().add (imageView);
        personalBoard.setBackground (new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));



        personalBoard.setCenter(slotsDevCards);
        personalBoard.setTop(faithTrack);
        personalBoard.setLeft(warehouseAndStrongbox);




        imageView.fitHeightProperty().bind (JavaFXApp.getFixedHeight ().multiply (0.93));
        this.setClosable (false);
        this.setContent(personalBoard);
    }


}
