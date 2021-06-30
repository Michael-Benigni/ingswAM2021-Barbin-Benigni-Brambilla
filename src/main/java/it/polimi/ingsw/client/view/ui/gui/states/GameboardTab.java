package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import it.polimi.ingsw.client.view.ui.gui.JsonImageLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameboardTab extends Tab {
    private final VBox market;
    private final ArrayList<RadioButton> marketButtons;
    private GridPane cardsGrid;


    public GameboardTab(){
        super("Game Board");
        this.cardsGrid = new GridPane();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ImageView marketContainer = new ImageView (loader.loadMarketImage());
        marketContainer.setPreserveRatio (true);
        marketContainer.setX (0);
        marketContainer.setY (0);
        marketContainer.fitHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.85));

        StackPane stackForMarket = new StackPane ();
        market = new VBox();
        marketButtons = new ArrayList<>();
        Canvas canvas = new Canvas (400, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D ();
        gc.setFill(Color.DARKSLATEGREY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.fillRoundRect (40, 120, 320, 250, 30, 30);
        gc.strokeRoundRect (40, 120, 320, 250, 30, 30);
        stackForMarket.getChildren ().addAll (marketContainer, canvas, market);
        stackForMarket.setAlignment (Pos.TOP_CENTER);

        HBox hBox = new HBox ();
        hBox.getChildren ().addAll (stackForMarket, cardsGrid);
        hBox.spacingProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.01));
        hBox.setAlignment (Pos.CENTER);
        Background background = new Background (new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY));
        hBox.setBackground (background);
        this.setClosable (false);
        this.setContent(hBox);
    }

    public VBox getMarket() {
        return market;
    }

    public ArrayList<RadioButton> getMarketButtons() {
        return marketButtons;
    }

    public GridPane getCardsGrid() {
        return cardsGrid;
    }
}
