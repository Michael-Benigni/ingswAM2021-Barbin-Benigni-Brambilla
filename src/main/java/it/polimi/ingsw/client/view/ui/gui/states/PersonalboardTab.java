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
import java.util.ArrayList;

public class PersonalboardTab extends Tab {

    private ArrayList<StackPane> slotsArray;
    private AnchorPane slotsDevCards;
    private BorderPane personalBoard;
    private HBox faithTrack;
    private VBox warehouseAndStrongbox;

    public PersonalboardTab(){
        super("Personal Board");
        StackPane content = new StackPane ();

        warehouseAndStrongbox = new VBox();
        faithTrack = new HBox();
        slotsArray = new ArrayList<> ();
        slotsDevCards = new AnchorPane ();
        personalBoard = new BorderPane();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ImageView imageView = new ImageView (loader.loadPersonalBoardImage ());
        imageView.setPreserveRatio (true);


        ImageView aaaaaaaa = new ImageView(loader.loadDevCardImage(45));
        aaaaaaaa.fitHeightProperty().bind(personalBoard.heightProperty ().multiply (0.37));
        aaaaaaaa.setPreserveRatio(true);
        aaaaaaaa.yProperty ().bind (personalBoard.heightProperty ().multiply (0.5));


        ImageView bbb = new ImageView(loader.loadDevCardImage(23));
        bbb.fitHeightProperty().bind(personalBoard.heightProperty ().multiply (0.37));
        bbb.setPreserveRatio(true);
        bbb.yProperty ().bind (personalBoard.heightProperty ().multiply (0.6));


        ImageView ccc = new ImageView(loader.loadDevCardImage(14));
        ccc.fitHeightProperty().bind(personalBoard.heightProperty ().multiply (0.37));
        ccc.setPreserveRatio(true);
        ccc.yProperty ().bind (personalBoard.heightProperty ().multiply (0.2));

        int numSlot = 3;
        HBox hBoxSlots = new HBox ();
        hBoxSlots.spacingProperty ().bind (personalBoard.widthProperty ().multiply (0.01));
        for (int i = 0; i <= numSlot; i++) {
            StackPane slot = new StackPane ();
            hBoxSlots.getChildren ().add (slot);
            slotsArray.add(slot);
        }

        slotsArray.get (0).getChildren ().add(aaaaaaaa);
        slotsArray.get (1).getChildren ().add (bbb);
        slotsArray.get (2).getChildren ().add (ccc);

        hBoxSlots.setAlignment (Pos.BOTTOM_RIGHT);
        //hBoxSlots.setPadding ();


        content.getChildren ().addAll (imageView, personalBoard);
        content.setBackground (new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));



        personalBoard.setCenter(hBoxSlots);
        personalBoard.setTop(faithTrack);
        personalBoard.setLeft(warehouseAndStrongbox);




        imageView.fitHeightProperty().bind (JavaFXApp.getFixedHeight ().multiply (0.93));
        this.setClosable (false);
        this.setContent(content);
    }


}
