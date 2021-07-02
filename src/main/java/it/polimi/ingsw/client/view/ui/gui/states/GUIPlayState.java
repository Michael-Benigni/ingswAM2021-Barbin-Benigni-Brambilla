package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.exceptions.IllegalInputException;
import it.polimi.ingsw.client.view.lightweightmodel.*;
import it.polimi.ingsw.client.view.moves.Move;
import it.polimi.ingsw.client.view.moves.PlayMove;
import it.polimi.ingsw.client.view.ui.cli.Colour;
import it.polimi.ingsw.client.view.ui.gui.*;
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
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GUIPlayState extends GUIState {
    private static Scene scene;
    private static GUIPlayState instance;
    private GUI gui;
    private GameboardTab gameboardTab;
    private PersonalboardTab personalboardTab;
    private TempContLeaderCardsTab tempContLeaderCardsTab;
    private ArrayList<Button> turnsButtons;
    private boolean isBuyCardTurn;
    private boolean isProductionTurn;
    private boolean isMarketTurn;
    private Button okButton;
    private AtomicReference<Move> chosenMove;
    private Button endTurnButton;
    private final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
    private final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    private VBox buttons;
    private VBox faithTrackVBox;


    private GUIPlayState() {
        instance = this;
    }

    @Override
    public Scene buildScene(GUI gui) {
        if (getWelcomeSceneInstance() == null) {
            this.gui = gui;
            HBox hBox = new HBox ();
            TabPane tabPane = new TabPane ();
            personalboardTab = new PersonalboardTab();
            gameboardTab = new GameboardTab();
            tempContLeaderCardsTab = new TempContLeaderCardsTab(gui);
            hBox.minWidthProperty ().bind (JavaFXApp.getFixedWidth ());
            hBox.minHeightProperty ().bind (JavaFXApp.getFixedHeight ());
            hBox.maxWidthProperty ().bind (JavaFXApp.getFixedWidth ());
            hBox.maxHeightProperty ().bind (JavaFXApp.getFixedHeight ());

            tabPane.getTabs ().addAll (personalboardTab, tempContLeaderCardsTab, gameboardTab);
            tabPane.minWidthProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.4));
            tabPane.minHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.6));
            tabPane.maxWidthProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.8));
            tabPane.maxHeightProperty ().bind (JavaFXApp.getFixedHeight ());

            buttons = getTurnsButtonsVBox ();

            faithTrackVBox = new VBox();
            faithTrackVBox.spacingProperty().bind(personalboardTab.getPersonalBoardBorderPane().heightProperty().multiply(0.1));
            buttons.getChildren().add(faithTrackVBox);

            buttons.maxWidthProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.2));
            buttons.maxHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.6));
            buttons.minWidthProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.1));
            buttons.minHeightProperty ().bind (JavaFXApp.getFixedHeight ());

            initBoardProduction();
            personalboardTab.initExtraProdLabel();

            hBox.getChildren ().addAll (tabPane, buttons) ;
            setSceneInstance (new Scene (hBox));
        }
        return getWelcomeSceneInstance();
    }

    public void initFaithTrack(){
        faithTrackVBox.getChildren().clear();
        Label faithTrackLabel = new Label("FAITH TRACK POSITIONS:");
        faithTrackLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
        faithTrackVBox.getChildren().add(faithTrackLabel);
        LWModel model = gui.getController().getModel();
        ArrayList<LWCell> faithTrack = model.getBoard().getFaithTrack();
        for(LWCell cell : faithTrack){
            ArrayList<String> usernamesInThisCell = cell.getPlayersInThisCell();
            for(String user : usernamesInThisCell){
                Label usernameLabel = new Label(user + "    Position: " + faithTrack.indexOf(cell));
                faithTrackVBox.getChildren().add(usernameLabel);
            }
        }
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

    public void initExtraProductionPowers(){
        ArrayList <LWXProductionPower> extraProductionPowers = gui.getController().getModel().getPersonalBoard().getExtraProductionPowers();
        JsonImageLoader loader = new JsonImageLoader(ClientPrefs.getPathToDB());
        for(LWXProductionPower prodPower : extraProductionPowers){
            ImageView resourceImage = new ImageView(loader.loadResourceImage(prodPower.getConsumedResource()));
            Button prodPowerButton = new Button("", resourceImage);
            prodPowerButton.setOnAction(actionEvent -> {
                gui.getInterpreter().addInteraction("numExtraPower", prodPower.getIndexOfPower().toString());
                //TODO: FINISH THIS
            });
            personalboardTab.getBoardAndExtraProductionsVBox().getChildren().add(prodPowerButton);
        }
    }

    public void initBoardProduction(){
        Button boardProductionButton = new Button("Board Production");
        boardProductionButton.setOnAction(actionEvent -> {
            chosenMove.set(PlayMove.BOARD_PRODUCTION.getMove());
            BoardProductionPopup.alert(gui);
            boardProductionButton.setDisable(true);
        });
        boardProductionButton.setDisable(true);
        personalboardTab.setBoardProductionButton(boardProductionButton);
        personalboardTab.getBoardAndExtraProductionsVBox().getChildren().add(boardProductionButton);
    }

    private VBox getTurnsButtonsVBox() {
        turnsButtons = new ArrayList<>();
        VBox turnButtons = new VBox (10);
        chosenMove = new AtomicReference<>();

        Button OK = new Button ("OK");
        OK.setMaxWidth (Double.MAX_VALUE);
        OK.setOnAction (e -> {
            if(isMarketTurn){
                new MoveService (chosenMove.get (), gui).start();
            }
            else if(isProductionTurn || isBuyCardTurn){
                int numOfPayments = gui.getInterpreter ().getNumInteractionsIn ("payActions");
                while (numOfPayments > 0) {
                    if (numOfPayments == 1)
                        gui.getInterpreter ().addInteraction ("stop", "S");
                    else
                        gui.getInterpreter ().addInteraction ("stop", "A");
                    numOfPayments--;
                }
                new MoveService (chosenMove.get (), gui).start();
                endTurnButton.setDisable(false);
                if (isProductionTurn) {
                    new MoveService (PlayMove.END_PRODUCTION.getMove(), gui).start();
                }
            }
        });

        this.okButton = OK;

        turnsButtons.add(OK);

        Button endTurn = new Button ("End Turn");
        endTurn.setMaxWidth (Double.MAX_VALUE);
        endTurn.setOnAction (e -> {
            setProductionTurn(false);
            setBuyCardTurn(false);
            setMarketTurn(false);
            new MoveService (PlayMove.END_TURN.getMove (), gui).start ();
            turnsButtons.forEach (node -> node.setDisable (true));
            personalboardTab.getBoardProductionButton().setDisable(true);
            personalboardTab.getSlotButtons().forEach(button -> button.setDisable(true));
        });

        this.endTurnButton = endTurn;

        turnsButtons.add(endTurn);

        Button marketTurn = new Button ("Market");
        marketTurn.setMaxWidth (Double.MAX_VALUE);
        marketTurn.setOnAction (e -> {
            setMarketTurn(true);
            gameboardTab.getMarketButtons().forEach (radioButton -> radioButton.setDisable (false));
            chosenMove.set (PlayMove.MARKET.getMove ());
            turnsButtons.stream()
                    .filter (b -> b != endTurn && b != OK)
                    .forEach (node -> node.setDisable (true));
        });

        turnsButtons.add(marketTurn);

        Button buyCardTurn = new Button ("Buy Card");
        buyCardTurn.setMaxWidth (Double.MAX_VALUE);
        buyCardTurn.setOnAction (e -> {
            setBuyCardTurn(true);
            enableCardsGrid(true);
            chosenMove.set (PlayMove.BUY_CARD.getMove ());
            turnsButtons.stream()
                    .filter (b -> b != endTurn)
                    .forEach (node -> node.setDisable (true));
        });

        turnsButtons.add(buyCardTurn);

        Button productionTurn = new Button ("Production");
        productionTurn.setMaxWidth (Double.MAX_VALUE);
        productionTurn.setOnAction (e -> {
            setProductionTurn(true);
            new MoveService(PlayMove.START_PRODUCTION.getMove(), gui).start();
            this.personalboardTab.getCardButtons ().forEach (button -> button.setDisable (false));
            this.personalboardTab.getStrongboxGrid ().getChildren ().forEach (button -> button.setDisable (false));
            turnsButtons.stream()
                    .filter (b -> b != OK).
                    forEach (node -> node.setDisable (true));
            if(!personalboardTab.getCardButtons().isEmpty())
                personalboardTab.getCardButtons().forEach(button -> { button.setDisable(false); });
            personalboardTab.getBoardProductionButton().setDisable(false);
        });

        turnsButtons.add(productionTurn);

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

    private void setBuyCardTurn(boolean buyCardTurn) {
        isBuyCardTurn = buyCardTurn;
    }

    private void setProductionTurn(boolean productionTurn) {
        isProductionTurn = productionTurn;
    }

    private void setMarketTurn(boolean marketTurn) {
        isMarketTurn = marketTurn;
    }

    private void enableCardsGrid(boolean isToEnable) {
        gameboardTab.getCardsGrid().getChildren ().forEach ((n) -> n.setDisable (!isToEnable));
    }

    public void initCardsGrid() throws IllegalInputException {
        gameboardTab.getCardsGrid().getChildren().clear();
        LWCardsGrid lwCardsGrid = gui.getController ().getModel ().getBoard ().getGrid ();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        for (int i = 0; i < lwCardsGrid.getRows (); i++) {
            for (int j = 0; j < lwCardsGrid.getColumns (); j++) {
                LWDevCard card = lwCardsGrid.getCard (i, j);
                Integer cardID = card.getId();

                if (cardID == null){
                    Label emptyDeckLabel = new Label("EMPTY DECK");
                    emptyDeckLabel.setAlignment(Pos.CENTER);
                    gameboardTab.getCardsGrid().add (emptyDeckLabel, j, i);
                }
                else{
                    ImageView cardWrapper = null;
                    cardWrapper = new ImageView (loader.loadDevCardImage (cardID));
                    cardWrapper.fitHeightProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.22));
                    cardWrapper.setPreserveRatio (true);
                    Button buttonCard = new Button ("", cardWrapper);
                    String finalJ = String.valueOf (j);
                    String finalI = String.valueOf (i);
                    buttonCard.setOnAction (e -> {
                        gui.getInterpreter ().addInteraction ("row", finalJ);
                        gui.getInterpreter ().addInteraction ("column", finalI);
                        ArrayList<Button> slotButtonsArray = personalboardTab.getSlotButtons();
                        for (Button button : slotButtonsArray){
                            System.out.println(button.toString());
                            button.setDisable(false);
                        }
                    });
                    gameboardTab.getCardsGrid().add (buttonCard, j, i);
                }

            }
        }
        gameboardTab.getCardsGrid().setAlignment (Pos.CENTER);
        enableCardsGrid (false);
    }

    public void initWarehouse(){
        personalboardTab.getWarehouseVBox().getChildren().clear();
        tempContLeaderCardsTab.getDepotBox ().getItems ().clear ();
        tempContLeaderCardsTab.getDepotBox ().setDisable (true);
        ArrayList<LWDepot> warehouse = gui.getController().getModel().getPersonalBoard().getWarehouse();
        tempContLeaderCardsTab.getDepotBox ().getItems ().addAll (warehouse.stream ().map (warehouse::indexOf).collect(Collectors.toList()));
        JsonImageLoader resourceLoader = new JsonImageLoader(ClientPrefs.getPathToDB());
        Label label = new Label ("Send to Temporary \n Container");
        label.setTextFill (Color.WHITE);
        label.setVisible (false);
        label.translateXProperty().bind(personalboardTab.getWarehouseAndStrongbox().
                heightProperty().multiply(0.15));
        label.translateYProperty().bind(personalboardTab.getWarehouseAndStrongbox().
                heightProperty().multiply(0.35));
        personalboardTab.getWarehouseVBox ().getChildren ().add (label);
        for(LWDepot depot : warehouse){
            LWResource resource = depot.getStoredResource();
            if(resource != null){
                ImageView resourceImage = new ImageView(resourceLoader.loadResourceImage(resource));
                resourceImage.fitHeightProperty().bind(personalboardTab.getWarehouseAndStrongbox().
                        heightProperty().multiply(0.06));
                resourceImage.setPreserveRatio (true);
                Button resourceWarehouseButton = new Button(((Integer)resource.getAmount()).toString(), resourceImage);
                resourceWarehouseButton.setStyle(IDLE_BUTTON_STYLE);
                resourceWarehouseButton.setOnMouseEntered(e -> {
                    resourceWarehouseButton.setStyle(HOVERED_BUTTON_STYLE);
                    if (!isProductionTurn && !isBuyCardTurn)
                        label.setVisible (true);
                });
                resourceWarehouseButton.setOnMouseExited(e -> {
                    resourceWarehouseButton.setStyle(IDLE_BUTTON_STYLE);
                    label.setVisible (false);
                });
                resourceWarehouseButton.setOnAction(actionEvent -> {
                    if (isProductionTurn || isBuyCardTurn) {
                        Integer amountSelected = PaymentPopup.howMuchPay (resource);
                        gui.getInterpreter ().addInteraction ("payActions", resource.getResourceType () + " " + amountSelected + " " + warehouse.indexOf (depot));
                    }
                    else {
                        GUIInterpreter interpreter = gui.getInterpreter ();
                        interpreter.addInteraction ("storeOrRemove", "STORE");
                        interpreter.addInteraction ("resource", resource.getResourceType () + " 1");
                        interpreter.addInteraction ("depotIdx", String.valueOf (warehouse.indexOf (depot)));
                        new MoveService (PlayMove.MOVE_RESOURCES.getMove (), gui).start ();
                    }
                });
                resourceWarehouseButton.translateYProperty().bind(personalboardTab.getWarehouseAndStrongbox().
                        heightProperty().multiply(0.42));
                resourceWarehouseButton.translateXProperty().bind(personalboardTab.getWarehouseAndStrongbox().
                        heightProperty().multiply(0.15));
                personalboardTab.getWarehouseVBox().getChildren().add(resourceWarehouseButton);
            }
            else{
                Label emptyDepotButton = new Label("");
                personalboardTab.getWarehouseVBox().getChildren().add(emptyDepotButton);
            }
        }
    }

    public void initStrongbox(){
        final int numRow = 2, numCol = 2;
        int k = 0;
        personalboardTab.getStrongboxGrid().getChildren().clear();
        ArrayList<LWResource> strongbox = gui.getController().getModel().getPersonalBoard().getStrongbox();
        JsonImageLoader resourceLoader = new JsonImageLoader(ClientPrefs.getPathToDB());
        for(int i = 0; i < numRow; i++){
            for(int j = 0; j < numCol; j++, k++){
                try{
                    LWResource resource = strongbox.get(k);
                    ImageView resourceImage = new ImageView(resourceLoader.loadResourceImage(resource));
                    resourceImage.fitHeightProperty().bind(personalboardTab.getWarehouseAndStrongbox().
                            heightProperty().multiply(0.08));
                    resourceImage.setPreserveRatio (true);
                    Button resourceStrongboxButton = new Button(((Integer) resource.getAmount()).toString(), resourceImage);
                    resourceStrongboxButton.setOnAction(actionEvent -> {
                        Integer amountSelected = PaymentPopup.howMuchPay (resource);
                        gui.getInterpreter ().addInteraction ("payActions", resource.getResourceType () + " " + amountSelected);
                    });
                    resourceStrongboxButton.setDisable (true);
                    resourceStrongboxButton.translateYProperty().bind(personalboardTab.getWarehouseAndStrongbox().
                            heightProperty().multiply(0.74));
                    resourceStrongboxButton.translateXProperty().bind(personalboardTab.getWarehouseAndStrongbox().
                            heightProperty().multiply(0.1));
                    personalboardTab.getStrongboxGrid().add(resourceStrongboxButton, j, i);
                }
                catch (IndexOutOfBoundsException ignored){

                }
            }
        }
    }

    public void initSlots(){

        personalboardTab.getSlotsHBox().getChildren().clear();
        ArrayList<ArrayList<LWDevCard>> slots = gui.getController ().getModel ().getPersonalBoard().getSlots();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        ArrayList<Button> slotButtons = new ArrayList<>();
        ArrayList<Button> cardButtons = new ArrayList<>();

        for (int i = 0; i < slots.size(); i ++){
            String finalI = String.valueOf (i);
            StackPane stackPane = new StackPane();

            Button slotButton = new Button("", stackPane);
            slotButton.setOnAction(actionEvent -> {
                gui.getInterpreter ().addInteraction ("slotIdx", finalI);
                okButton.setDisable(false);
            });
            slotButton.setDisable(true);
            slotButtons.add(slotButton);

            if(slots.get(i).isEmpty()){
                slotButton.setText("          EMPTY SLOT          ");
            }

            for (int j = 0; j < slots.get(i).size(); j ++){
                ImageView cardWrapper = new ImageView(loader.loadDevCardImage(slots.get(i).get(j).getId()));
                cardWrapper.fitHeightProperty().
                        bind(personalboardTab.getPersonalBoardBorderPane().heightProperty ().multiply (0.37));
                cardWrapper.setPreserveRatio (true);
                if(j == slots.get(i).size() - 1){
                    Button slotCardButton = new Button("", cardWrapper);
                    slotCardButton.setOnAction(actionEvent -> {
                        gui.getInterpreter ().addInteraction ("numSlot", finalI);
                    });
                    slotCardButton.setDisable (true);
                    cardButtons.add(slotCardButton);
                    stackPane.getChildren().add(slotCardButton);
                    slotCardButton.translateYProperty().bind(personalboardTab.getPersonalBoardBorderPane().
                            heightProperty().multiply(-j * 0.08));
                }
                else{
                    stackPane.getChildren().add(cardWrapper);
                    cardWrapper.translateYProperty().bind(personalboardTab.getPersonalBoardBorderPane().
                            heightProperty().multiply(-j * 0.08));
                }
            }
            personalboardTab.getSlotsHBox().getChildren().add(slotButton);
        }
        personalboardTab.setSlotButtons(slotButtons);
        personalboardTab.setCardButtons(cardButtons);
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
        marbleOnSlide.translateXProperty().bind(personalboardTab.getPersonalBoardBorderPane().heightProperty().multiply(0.2));
        gameboardTab.getMarket().getChildren ().add (0, marbleOnSlide);
        gameboardTab.getMarket().setAlignment(Pos.CENTER_LEFT);
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
        canvas.heightProperty().bind(gameboardTab.getMarket().heightProperty().multiply(0.055));
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

    public void resetButtonsForTurnChanging() {
        for (Button turnButton : turnsButtons){
            turnButton.setDisable(false);
        }
    }

    public void initTempContainer() {
        tempContLeaderCardsTab.getResources ().getChildren ().clear ();
        ArrayList<LWResource> storableResources = gui.getController ().getModel ().getPersonalBoard ().
                getTemporaryContainer ().getStorableResources ();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        for (LWResource resource : storableResources) {
            ImageView resourceImage = new ImageView (loader.loadResourceImage (resource));
            resourceImage.fitHeightProperty().bind(tempContLeaderCardsTab.getResources().heightProperty().multiply(0.3));
            resourceImage.setPreserveRatio(true);
            Button resourceBtn = new Button (((Integer)resource.getAmount()).toString(), resourceImage);
            tempContLeaderCardsTab.getResources ().getChildren ().add (resourceBtn);
            ArrayList<Integer> amounts = new ArrayList<> ();
            for (int i = 1; i <= resource.getAmount (); i++)
                amounts.add (i);
            resourceBtn.setStyle(IDLE_BUTTON_STYLE);
            resourceBtn.setOnMouseEntered(e -> resourceBtn.setStyle(HOVERED_BUTTON_STYLE));
            resourceBtn.setOnMouseExited(e -> resourceBtn.setStyle(IDLE_BUTTON_STYLE));
            resourceBtn.setOnAction (e -> {
                tempContLeaderCardsTab.getAmountBox().getItems().clear();
                tempContLeaderCardsTab.setSelectedResource(resource.getResourceType());
                tempContLeaderCardsTab.getAmountBox ().getItems ().addAll (amounts);
                tempContLeaderCardsTab.getAmountBox ().setValue (amounts.get (0));
                tempContLeaderCardsTab.getAmountBox ().setDisable (false);
                tempContLeaderCardsTab.getDepotBox ().setDisable (false);
                tempContLeaderCardsTab.getSendToWarehouse ().setDisable (false);
            });
        }
        int numEmptyResources = gui.getController ().getModel ().getPersonalBoard ().
                getTemporaryContainer ().getEmptyResources ();
        for (int i = 0; i < numEmptyResources; i++) {
            Canvas whiteMarble = getMarbleCanvas (Colour.WHITE.name ());
            Button whiteMarbleBtn = new Button ("", whiteMarble);
            tempContLeaderCardsTab.getResources ().getChildren ().add (whiteMarbleBtn);
            whiteMarbleBtn.setOnAction (e -> {
                ArrayList<LWWMPower> powers = gui.getController ().getModel ().getPersonalBoard ().getWhiteMarblePowers ();
                if (!powers.isEmpty ())
                    TrasformWhiteMarblePopup.alert(powers, gui);
            });
        }
    }

    public void initSlotLeaderCards() {
        tempContLeaderCardsTab.getLeaderCards ().getChildren ().clear ();
        ArrayList<LWLeaderCard> activeCards = gui.getController ().getModel ().getPersonalBoard ().getLeaderCardsPlayed ();
        ArrayList<LWLeaderCard> inactiveCards = gui.getController ().getModel ().getPersonalBoard ().getLeaderCardsNotPlayed ();
        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        for (LWLeaderCard card : inactiveCards) {
            ImageView cardImage = new ImageView (loader.loadLeaderCardImage (card.getID()));
            cardImage.fitHeightProperty ().bind (tempContLeaderCardsTab.getTabContent ().heightProperty ().multiply (0.4));
            cardImage.setPreserveRatio (true);
            HBox playOrDiscardBtns = new HBox ();
            Button playBtn = new Button ("Play");
            playBtn.setOnAction(actionEvent -> {
                gui.getInterpreter().addInteraction("playOrDiscard", "play");
                gui.getInterpreter().addInteraction("numInSlot", ((Integer) card.getSlotIndex()).toString());
                new MoveService(PlayMove.LEADER.getMove(), gui).start();
            });
            Button discardBtn = new Button ("Discard");
            discardBtn.setOnAction(actionEvent -> {
                if (gui.getController ().getModel ().getInfoMatch ().getNumTurn ().equals (1)) {
                    gui.getInterpreter ().addInteraction ("cardIndex", ((Integer) card.getSlotIndex ()).toString ());
                    new MoveService (PlayMove.DISCARD_LEADER_CARD_FIRST_TURN.getMove (), gui).start ();
                } else {
                    gui.getInterpreter ().addInteraction ("playOrDiscard", "discard");
                    gui.getInterpreter ().addInteraction ("numInSlot", ((Integer) card.getSlotIndex ()).toString ());
                    new MoveService (PlayMove.LEADER.getMove (), gui).start ();
                }
            });
            playOrDiscardBtns.getChildren ().addAll (playBtn, discardBtn);
            playOrDiscardBtns.spacingProperty ().bind (cardImage.fitWidthProperty ().multiply (0.1));
            VBox btnsAndCard = new VBox ();
            btnsAndCard.getChildren ().addAll (playOrDiscardBtns, cardImage);
            tempContLeaderCardsTab.getLeaderCards ().getChildren ().add (btnsAndCard);
        }
        for (LWLeaderCard card : activeCards) {
            ImageView cardImage = new ImageView (loader.loadLeaderCardImage (card.getID()));
            cardImage.fitHeightProperty ().bind (tempContLeaderCardsTab.getTabContent ().heightProperty ().multiply (0.4));
            cardImage.setPreserveRatio (true);
            VBox cardAndActiveLabel = new VBox ();
            Label activeLabel = new Label("ACTIVE");
            activeLabel.setTextAlignment(TextAlignment.CENTER);
            activeLabel.setAlignment(Pos.CENTER);
            cardAndActiveLabel.getChildren ().addAll (activeLabel, cardImage);
            tempContLeaderCardsTab.getLeaderCards ().getChildren ().add (cardAndActiveLabel);
        }
    }
}
