package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.lightweightmodel.LWCardsGrid;
import it.polimi.ingsw.client.view.lightweightmodel.LWDevCard;
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
    private GameboardTab gameboardTab;
    private PersonalBoardTab personalboardTab;
    private TempContLeaderCardsTab tempContLeaderCardsTab;

    private GUIPlayState() {
        instance = this;
    }

    @Override
    public Scene buildScene(GUI gui) {
        if (getWelcomeSceneInstance() == null) {
            this.gui = gui;
            HBox hBox = new HBox ();
            TabPane tabPane = new TabPane ();
            personalboardTab = new PersonalBoardTab();
            gameboardTab = new GameboardTab();
            tempContLeaderCardsTab = new TempContLeaderCardsTab();
            tabPane.getTabs ().addAll (personalboardTab, tempContLeaderCardsTab, gameboardTab);
            tabPane.prefWidthProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.9));
            hBox.spacingProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.02));
            hBox.getChildren ().addAll (tabPane, getTurnsButtonsVBox ()) ;
            setSceneInstance (new Scene (hBox));
        }
        return getWelcomeSceneInstance();
    }



    @Override
    protected void setSceneInstance(Scene scene) {
        if (GUIPlayState.scene == null)
            GUIPlayState.scene = scene;
    }

    @Override
    protected Scene getWelcomeSceneInstance() {
        return scene;
    }

    @Override
    public GUIState getNextState() {
        return new GUIGameOverState();
    }

    private VBox getTurnsButtonsVBox() {
        VBox turnButtons = new VBox (10);
        AtomicReference<Move> chosenMove = new AtomicReference<>();

        Button OK = new Button ("OK");
        OK.setMaxWidth (Double.MAX_VALUE);
        OK.setOnAction (e -> new MoveService (chosenMove.get (), gui));

        Button endTurn = new Button ("End Turn");
        endTurn.setMaxWidth (Double.MAX_VALUE);
        endTurn.setOnAction (e -> {
            new MoveService (PlayMove.END_TURN.getMove (), gui).start ();
            turnButtons.getChildren ().stream().forEach (node -> node.setDisable (true));
        });

        Button marketTurn = new Button ("Market");
        marketTurn.setMaxWidth (Double.MAX_VALUE);
        marketTurn.setOnAction (e -> {
            gameboardTab.getMarketButtons().forEach (radioButton -> radioButton.setDisable (false));
            chosenMove.set (PlayMove.MARKET.getMove ());
            turnButtons.getChildren ().stream()
                    .filter (b -> b != endTurn && b != OK)
                    .forEach (node -> node.setDisable (true));
        });

        Button buyCardTurn = new Button ("Buy Card");
        buyCardTurn.setMaxWidth (Double.MAX_VALUE);
        buyCardTurn.setOnAction (e -> {
            enableCardsGrid(true);
            chosenMove.set (PlayMove.BUY_CARD.getMove ());
            turnButtons.getChildren ().stream()
                    .filter (b -> b != endTurn && b != OK)
                    .forEach (node -> node.setDisable (true));
        });

        Button productionTurn = new Button ("Production");
        productionTurn.setMaxWidth (Double.MAX_VALUE);
        productionTurn.setOnAction (e -> {
            chosenMove.set (PlayMove.BUY_CARD.getMove ());
            turnButtons.getChildren ().stream()
                    .filter (b -> b != endTurn && b != OK).
                    forEach (node -> node.setDisable (true));
        });


        Label buttonsLabel = new Label ("Choose your Turn Type!");
        buttonsLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        VBox okEndButtons = new VBox ();
        okEndButtons.setAlignment (Pos.BOTTOM_CENTER);
        okEndButtons.getChildren ().addAll (OK, endTurn);

        turnButtons.setSpacing(10);
        turnButtons.setPadding(new Insets(20, 20, 10, 20));
        turnButtons.setBackground (new Background (new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        turnButtons.setAlignment (Pos.TOP_CENTER);
        turnButtons.getChildren ().addAll (
                buttonsLabel,
                marketTurn,
                buyCardTurn,
                productionTurn,
                okEndButtons
        );

        return turnButtons;
    }

    private void enableCardsGrid(boolean isToEnable) {
        gameboardTab.getCardsGrid().getChildren ().forEach ((n) -> n.setDisable (!isToEnable));
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
                gameboardTab.getCardsGrid().add (buttonCard, j, i);
            }
        }
        gameboardTab.getCardsGrid().setAlignment (Pos.CENTER);
        enableCardsGrid (false);
    }

    public void initSlots(){
        ArrayList<ArrayList<LWDevCard>> slots = gui.getController ().getModel ().getPersonalBoard().getSlots();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                ImageView cardWrapper = null;
                cardWrapper = new ImageView(loader.loadDevCardImage(slots.get(i).get(j).getId()));
                cardWrapper.fitHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.35));
                cardWrapper.setPreserveRatio (true);
            }
        }

    }

    public void initMarketGrid() {
        gameboardTab.getMarket().getChildren ().clear ();
        ToggleGroup columnGroup = new ToggleGroup ();
        ToggleGroup rowGroup = new ToggleGroup ();
        GridPane marketGrid = new GridPane ();
        LWMarket lwMarket = gui.getController ().getModel ().getBoard ().getMarket ();
        Canvas marbleOnSlideCanvas = getMarbleCanvas (lwMarket.getMarbleOnSlide ().name ());
        VBox marbleOnSlide = new VBox ();
        marbleOnSlide.getChildren ().add (marbleOnSlideCanvas);
        marbleOnSlide.setPadding (new Insets (0, 0, 0, 130));
        int i, j;
        for (i = 0; i < lwMarket.getRows(); i++) {
            for (j = 0; j < lwMarket.getColumns (); j++) {
                Canvas canvas = getMarbleCanvas (lwMarket.getMarbles ().get (i).get (j).name ());
                marketGrid.add (canvas, j, i);
                RadioButton columnButton = getRadioButton (columnGroup, rowGroup, j, "column");
                marketGrid.add (columnButton, j, lwMarket.getRows ());
                gameboardTab.getMarketButtons().add (columnButton);
            }
            RadioButton rowButton = getRadioButton (rowGroup, columnGroup, i, "row");
            marketGrid.add (rowButton, j, i);
            gameboardTab.getMarketButtons().add (rowButton);
        }
        marketGrid.setVgap (10);
        marketGrid.setHgap (10);
        marketGrid.setAlignment (Pos.CENTER);
        gameboardTab.getMarket().getChildren ().add (0, marbleOnSlide);
        gameboardTab.getMarket().getChildren ().add (1, marketGrid);
        gameboardTab.getMarket().setAlignment (Pos.TOP_LEFT);
        gameboardTab.getMarket().setPadding (new Insets (130, 0, 0, 50));
        gameboardTab.getMarketButtons().forEach (radioButton -> radioButton.setDisable (true));
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
