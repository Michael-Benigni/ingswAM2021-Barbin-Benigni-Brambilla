package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.lightweightmodel.LWCardsGrid;
import it.polimi.ingsw.client.view.lightweightmodel.LWMarket;
import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.moves.PlayMove;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import it.polimi.ingsw.client.view.ui.gui.JsonImageLoader;
import it.polimi.ingsw.client.view.ui.gui.MoveService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class GUIPlayState extends GUIState {
    private static Scene scene;
    private static GUIPlayState instance;
    private GUI gui;
    private GridPane cardsGrid;
    private VBox market;
    private ArrayList<RadioButton> marketButtons;

    private GUIPlayState() {
        instance = this;
    }

    @Override
    public Scene buildScene(GUI gui) {
        if (getSceneInstance () == null) {
            this.gui = gui;
            HBox hBox = new HBox ();
            TabPane tabPane = new TabPane ();
            Tab tabPersonalBoard = getPersonalBoardTab ();
            Tab tabGameBoard = getGameBoardTab ();
            Tab tabTempContainerAndLeaderCards = getTempContainerAndLeaderCardsTab();
            tabPane.getTabs ().addAll (tabPersonalBoard, tabTempContainerAndLeaderCards, tabGameBoard);
            tabPane.prefWidthProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.9));
            hBox.spacingProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.02));
            hBox.getChildren ().addAll (tabPane, getTurnsButtonsVBox ()) ;
            setSceneInstance (new Scene (hBox));
        }
        return getSceneInstance ();
    }

    private Tab getTempContainerAndLeaderCardsTab() {
        return new Tab ("TemporaryContainer And Leader Cards Slot");
    }

    @Override
    protected void setSceneInstance(Scene scene) {
        if (GUIPlayState.scene == null)
            GUIPlayState.scene = scene;
    }

    @Override
    protected Scene getSceneInstance() {
        return scene;
    }

    @Override
    public GUIState getNextState() {
        return new GUIGameOverState();
    }

    private Tab getPersonalBoardTab() {
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ImageView imageView = new ImageView (loader.loadPersonalBoardImage ());
        imageView.setPreserveRatio (true);
        HBox hBox = new HBox ();
        hBox.getChildren ().add (imageView);
        hBox.setBackground (new Background (new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        imageView.fitHeightProperty().bind (JavaFXApp.getFixedHeight ().multiply (0.93));
        Tab tab = new Tab ("Personal Board", hBox);
        tab.setClosable (false);
        return tab;
    }

    private Tab getGameBoardTab() {
        this.cardsGrid = new GridPane ();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ImageView marketContainer = new ImageView (loader.loadMarketImage());
        marketContainer.setPreserveRatio (true);
        marketContainer.setX (0);
        marketContainer.setY (0);
        marketContainer.fitHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.91));

        StackPane stackForMarket = new StackPane ();
        market = new VBox ();
        marketButtons = new ArrayList<> ();
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

        Tab tab = new Tab ("Game Board", hBox);
        tab.setClosable (false);
        return tab;
    }

    private VBox getTurnsButtonsVBox() {
        AtomicReference<Move> chosenMove = new AtomicReference<>();

        Button marketTurn = new Button ("Market");
        marketTurn.setMaxWidth (Double.MAX_VALUE);
        marketTurn.setOnAction (e -> {
            marketButtons.forEach (radioButton -> radioButton.setDisable (false));
            chosenMove.set (PlayMove.MARKET.getMove ());
        });

        Button buyCardTurn = new Button ("Buy Card");
        buyCardTurn.setMaxWidth (Double.MAX_VALUE);
        buyCardTurn.setOnAction (e -> {
            enableCardsGrid(true);
            chosenMove.set (PlayMove.BUY_CARD.getMove ());
        });

        Button productionTurn = new Button ("Production");
        productionTurn.setMaxWidth (Double.MAX_VALUE);
        productionTurn.setOnAction (e -> {});

        Button OK = new Button ("OK");
        OK.setMaxWidth (Double.MAX_VALUE);
        OK.setOnAction (e -> new MoveService (chosenMove.get (), gui));

        Button endTurn = new Button ("End Turn");
        endTurn.setMaxWidth (Double.MAX_VALUE);
        endTurn.setOnAction (e -> new MoveService (PlayMove.END_TURN.getMove (), gui));

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

    private void enableCardsGrid(boolean isToEnable) {
        cardsGrid.getChildren ().forEach ((n) -> n.setDisable (!isToEnable));
    }

    public void initCardsGrid() {
        LWCardsGrid lwCardsGrid = gui.getController ().getModel ().getBoard ().getGrid ();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        for (int i = 0; i < lwCardsGrid.getRows (); i++) {
            for (int j = 0; j < lwCardsGrid.getColumns (); j++) {
                ImageView cardWrapper = null;
                try {
                    cardWrapper = new ImageView (loader.loadDevCardImage (lwCardsGrid.getCard (i, j).getId ()));
                } catch (IllegalInputException e) {
                    e.printStackTrace ();
                }
                cardWrapper.fitHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.25));
                cardWrapper.setPreserveRatio (true);
                Button buttonCard = new Button ("", cardWrapper);
                String finalJ = String.valueOf (j);
                String finalI = String.valueOf (i);
                buttonCard.setOnAction (e -> {
                    gui.getInterpreter ().addInteraction ("row", finalJ);
                    gui.getInterpreter ().addInteraction ("column", finalI);
                    //TODO: to enable the slots buttons
                });
                cardsGrid.add (buttonCard, j, i);
            }
        }
        cardsGrid.setAlignment (Pos.CENTER);
        enableCardsGrid (false);
    }

    public void initMarketGrid() {
        market.getChildren ().clear ();
        ToggleGroup columnGroup = new ToggleGroup ();
        ToggleGroup rowGroup = new ToggleGroup ();
        GridPane marketGrid = new GridPane ();
        LWMarket lwMarket = gui.getController ().getModel ().getBoard ().getMarket ();
        Canvas marbleOnSlideCanvas = getMarbleCanvas (lwMarket.getMarbleOnSlide ().name ());
        int i, j;
        for (i = 0; i < lwMarket.getRows(); i++) {
            for (j = 0; j < lwMarket.getColumns (); j++) {
                Canvas canvas = getMarbleCanvas (lwMarket.getMarbles ().get (i).get (j).name ());
                marketGrid.add (canvas, j, i);
                RadioButton columnButton = getRadioButton (columnGroup, rowGroup, j, "column");
                marketGrid.add (columnButton, j, lwMarket.getRows ());
                marketButtons.add (columnButton);
            }
            RadioButton rowButton = getRadioButton (rowGroup, columnGroup, i, "row");
            marketGrid.add (rowButton, j, i);
            marketButtons.add (rowButton);
        }
        marketGrid.setVgap (10);
        marketGrid.setHgap (10);
        marketGrid.setAlignment (Pos.CENTER);
        market.getChildren ().add (0, marbleOnSlideCanvas);
        market.getChildren ().add (1, marketGrid);
        market.setAlignment (Pos.TOP_LEFT);
        market.setPadding (new Insets (100, 0, 0, 150));
        marketButtons.forEach (radioButton -> radioButton.setDisable (true));
    }

    private RadioButton getRadioButton(ToggleGroup owner, ToggleGroup mutualExclusive, int k, String rowOrColumn) {
        RadioButton columnButton = new RadioButton ();
        columnButton.setToggleGroup (owner);
        String finalK = String.valueOf (k);
        columnButton.setOnAction (e -> {
            if (mutualExclusive.getSelectedToggle () != null)
                mutualExclusive.getSelectedToggle ().setSelected (false);
            gui.getInterpreter ().addInteraction ("rowOrColumn", rowOrColumn);
            gui.getInterpreter ().addInteraction ("numRowOrColumn", finalK);
        });
        return columnButton;
    }

    private Canvas getMarbleCanvas(String colourName) {
        Canvas canvas = new Canvas (50, 50);
        GraphicsContext marbleGrCtx = canvas.getGraphicsContext2D();
        Color color = findColourbyName (colourName);
        marbleGrCtx.setFill(color);
        marbleGrCtx.setStroke(Color.BLACK);
        marbleGrCtx.setLineWidth(2);
        marbleGrCtx.fillOval(5, 5, 30, 30);
        marbleGrCtx.strokeOval(5, 5, 30, 30);
        return canvas;
    }

    private Color findColourbyName(String colourName) {
        return Color.web (colourName.toLowerCase ());
    }

    public static GUIPlayState getInstance() {
        if (instance == null)
            return new GUIPlayState ();
        return instance;
    }
}
