package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.lightweightmodel.LWCardsGrid;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import it.polimi.ingsw.utils.config.JsonHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileNotFoundException;

public class GUIPlayState extends GUIState {
    private static Scene scene;
    private static GUIPlayState instance;
    private GUI gui;
    private GridPane cardsGrid;

    private GUIPlayState() {
        instance = this;
    }

    @Override
    protected void setSceneInstance(Scene scene) {
        if (GUIPlayState.scene == null)
            GUIPlayState.scene = scene;
    }

    @Override
    public GUIState getNextState() {
        return new GUIGameOverState();
    }

    @Override
    public Scene buildScene(GUI gui) {
        if (getSceneInstance () == null) {
            this.gui = gui;
            HBox hBox = new HBox ();
            TabPane tabPane = new TabPane ();
            Tab tabPersonalBoard = getPersonalBoardTab ();
            Tab tabGameBoard = getGameBoardTab ();
            tabPane.getTabs ().addAll (tabPersonalBoard, tabGameBoard);
            tabPane.prefWidthProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.9));
            hBox.spacingProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.02));
            hBox.getChildren ().addAll (tabPane, getTurnsButtonsVBox ()) ;
            setSceneInstance (new Scene (hBox));
        }
        return getSceneInstance ();
    }

    @Override
    protected Scene getSceneInstance() {
        return scene;
    }

    private Tab getPersonalBoardTab() {
        Image image = new Image (getClass ().getResourceAsStream ("/images/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png"));
        ImageView imageView = new ImageView (image);
        imageView.setPreserveRatio (true);
        HBox hBox = new HBox ();
        hBox.getChildren ().add (imageView);
        hBox.setBackground (new Background (new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        imageView.fitHeightProperty().bind (JavaFXApp.getFixedHeight ().multiply (0.93));
        Tab tab = new Tab ("Personal Board", hBox);
        //grid.addColumn (0, imageView);
        //grid.addColumn (1, getTurnsButtonsVBox ());

        // create a background image
        /*BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition (Side.LEFT, 0, false, Side.BOTTOM, 0, false),
                new BackgroundSize (width / 2, height / 2, false, false, true, true)
        );
        Background background = new Background(backgroundimage);
        BorderPane borderPane = new BorderPane ();
        borderPane.setBackground (background);*/

        //VBox vBox = getTurnsButtonsVBox (width, image);
        //grid.add (vBox, 0, 0);

        tab.setClosable (false);
        return tab;
    }

    private VBox getTurnsButtonsVBox() {
        Button marketTurn = new Button ("Market");
        marketTurn.setMaxWidth (Double.MAX_VALUE);
        marketTurn.setOnAction (e -> {});

        Button buyCardTurn = new Button ("Buy Card");
        buyCardTurn.setMaxWidth (Double.MAX_VALUE);
        buyCardTurn.setOnAction (e -> {});

        Button productionTurn = new Button ("Production");
        productionTurn.setMaxWidth (Double.MAX_VALUE);
        productionTurn.setOnAction (e -> {});

        Label buttonsLabel = new Label ("Choose your Turn Type!");
        buttonsLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        VBox buttons = new VBox (10);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(20, 20, 10, 20));
        buttons.setBackground (new Background (new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttons.setAlignment (Pos.TOP_CENTER);
        buttons.getChildren ().addAll (buttonsLabel, marketTurn, buyCardTurn, productionTurn);

        return buttons;
    }

    private Tab getGameBoardTab() {
        Image image = new Image (getClass ().getResourceAsStream ("/images/punchboard/plancia portabiglie.png"));
        ImageView market = new ImageView (image);
        market.setPreserveRatio (true);
        market.fitHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.91));


        this.cardsGrid = new GridPane ();

        HBox hBox = new HBox ();
        hBox.getChildren ().addAll (market, cardsGrid);
        hBox.spacingProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.01));
        hBox.setAlignment (Pos.CENTER);
        Background background = new Background (new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY));
        hBox.setBackground (background);

        Tab tab = new Tab ("Game Board", hBox);
        tab.setClosable (false);
        return tab;
    }

    private void initCardsGrid(GridPane grid) throws IllegalInputException, FileNotFoundException {
        LWCardsGrid lwCardsGrid = gui.getController ().getModel ().getBoard ().getGrid ();
        JsonHandler jsonHandler = new JsonHandler (ClientPrefs.getPathToDB());
        for (int i = 0; i < 3 /*lwCardsGrid.getRows ()*/; i++) {
            for (int j = 0; j < 4 /*lwCardsGrid.getColumns ()*/; j++) {
                //String fileImgPath = (String) jsonHandler.getAsJavaObjectFromJSON (String.class, String.valueOf (lwCardsGrid.getCard (i, j).getId ()));
                Image card = new Image (this.getClass ().getResourceAsStream ("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
                ImageView cardWrapper = new ImageView (card);
                cardWrapper.fitHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.25));
                cardWrapper.setPreserveRatio (true);
                Button buttonCard = new Button ("", cardWrapper);
                grid.add (buttonCard, j, i);
            }
        }
        grid.setAlignment (Pos.CENTER);
    }

    public static GUIPlayState getInstance() {
        if (instance == null)
            return new GUIPlayState ();
        return instance;
    }
}
