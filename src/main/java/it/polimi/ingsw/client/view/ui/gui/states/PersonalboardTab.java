
package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import it.polimi.ingsw.client.view.ui.gui.JsonImageLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class PersonalboardTab extends Tab {

    private BorderPane personalBoardBorderPane;
    private HBox faithTrackHBox;
    private VBox warehouseAndStrongbox;
    private HBox slotsHBox;
    private ArrayList<Button> slotButtons;
    private ArrayList<Button> cardButtons;
    private GridPane strongboxGrid;
    private VBox warehouseVBox;
    private VBox boardAndExtraProductionsVBox;
    private Button boardProductionButton;
    private Button extraProductionButton;

    public VBox getWarehouseVBox() {
        return warehouseVBox;
    }

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

    public Button getBoardProductionButton() {
        return boardProductionButton;
    }

    public void setBoardProductionButton(Button boardProductionButton) {
        this.boardProductionButton = boardProductionButton;
    }

    public Button getExtraProductionButton() {
        return extraProductionButton;
    }

    public void setExtraProductionButton(Button extraProductionButton) {
        this.extraProductionButton = extraProductionButton;
    }

    public VBox getBoardAndExtraProductionsVBox() {
        return boardAndExtraProductionsVBox;
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
        warehouseVBox = new VBox();
        boardAndExtraProductionsVBox = new VBox();

        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ImageView imageView = new ImageView (loader.loadPersonalBoardImage ());
        imageView.setPreserveRatio (true);
        imageView.fitHeightProperty().bind (JavaFXApp.getFixedHeight ().multiply (0.90));

        content.getChildren ().addAll (imageView, personalBoardBorderPane);
        content.setBackground (new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

        warehouseAndStrongbox.getChildren().add(warehouseVBox);
        warehouseAndStrongbox.getChildren().add(strongboxGrid);
        //TODO: FOR MICHAEL ---> warehouseAndStrongbox.spacingProperty().bind(personalBoardBorderPane.heightProperty().multiply(0.3));


        slotsHBox.spacingProperty().bind(personalBoardBorderPane.widthProperty().multiply(0.025));
        slotsHBox.translateYProperty().bind(this.getPersonalBoardBorderPane().heightProperty().multiply(0.5));
        slotsHBox.translateXProperty().bind(this.getPersonalBoardBorderPane().widthProperty().multiply(0.40).
                subtract (warehouseAndStrongbox.widthProperty ()));



        personalBoardBorderPane.setTop(faithTrackHBox);
        personalBoardBorderPane.setLeft(warehouseAndStrongbox);
        personalBoardBorderPane.setCenter(slotsHBox);
        personalBoardBorderPane.setBottom(boardAndExtraProductionsVBox);

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

    public void initExtraProdLabel(){
        Label extraProductionLabel = new Label("Extra Production\nPowers");
        extraProductionLabel.setAlignment(Pos.CENTER);
        extraProductionLabel.setTextAlignment(TextAlignment.CENTER);
        extraProductionLabel.backgroundProperty().setValue(new Background(new BackgroundFill(Paint.valueOf("ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));

        boardAndExtraProductionsVBox.getChildren().add(extraProductionLabel);
        boardAndExtraProductionsVBox.translateXProperty().bind(personalBoardBorderPane.widthProperty().multiply(0.28));
        boardAndExtraProductionsVBox.translateYProperty().bind(personalBoardBorderPane.widthProperty().multiply(-0.35));
    }
}
