
package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import it.polimi.ingsw.client.view.ui.gui.JsonImageLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PersonalboardTab extends Tab {

    private BorderPane personalBoardBorderPane;
    private HBox faithTrackHBox;
    private VBox warehouseAndStrongbox;
    private HBox slotsHBox;
    private ArrayList<Button> slotButtons;
    private ArrayList<Button> cardButtons;
    private GridPane strongboxGrid;

    public GridPane getStrongboxGrid() {
        return strongboxGrid;
    }

    public ArrayList<Button> getSlotButtons() {
        return slotButtons;
    }

    public ArrayList<Button> getCardButtons() {
        return cardButtons;
    }

    public HBox getSlotsHBox() {
        return slotsHBox;
    }

    public BorderPane getPersonalBoardBorderPane() {
        return personalBoardBorderPane;
    }

    public HBox getFaithTrackHBox() {
        return faithTrackHBox;
    }

    public VBox getWarehouseAndStrongbox() {
        return warehouseAndStrongbox;
    }

    public PersonalboardTab(){
        super("Personal Board");

        StackPane content = new StackPane ();
        warehouseAndStrongbox = new VBox();
        faithTrackHBox = new HBox();
        slotsHBox = new HBox();
        personalBoardBorderPane = new BorderPane();
        slotButtons = new ArrayList<>();
        cardButtons = new ArrayList<>();
        strongboxGrid = new GridPane();

        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ImageView imageView = new ImageView (loader.loadPersonalBoardImage ());
        imageView.setPreserveRatio (true);
        imageView.fitHeightProperty().bind (JavaFXApp.getFixedHeight ().multiply (0.90));

        content.getChildren ().addAll (imageView, personalBoardBorderPane);
        content.setBackground (new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

        warehouseAndStrongbox.getChildren().add(strongboxGrid);

        personalBoardBorderPane.setTop(faithTrackHBox);
        personalBoardBorderPane.setLeft(warehouseAndStrongbox);
        personalBoardBorderPane.setCenter(slotsHBox);

        slotsHBox.spacingProperty().bind(personalBoardBorderPane.widthProperty().multiply(0.01));
        slotsHBox.translateYProperty().bind(this.getPersonalBoardBorderPane().heightProperty().multiply(0.5));
        slotsHBox.translateXProperty().bind(this.getPersonalBoardBorderPane().widthProperty().multiply(0.38));

        this.setClosable (false);
        this.setContent(content);
    }

    public void setSlotButtons(ArrayList<Button> buttons){
        this.slotButtons.clear();
        this.slotButtons.addAll(buttons);
    }

    public void setCardButtons(ArrayList<Button> buttons){
        this.cardButtons.clear();
        this.cardButtons.addAll(buttons);
    }
}
