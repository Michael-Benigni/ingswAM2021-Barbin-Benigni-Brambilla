package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.lightweightmodel.LWDepot;
import it.polimi.ingsw.client.view.ui.gui.GUI;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TempContLeaderCardsTab extends Tab {
    private final HBox leaderCards;
    private final StackPane tempContainerStack;
    private final HBox tempContainerHBox;
    private final ChoiceBox<Integer> amountBox;
    private final ChoiceBox<Integer> depotBox;
    private final Button sendToWarehouse;
    private final HBox resources;

    private final VBox tabContent;

    public TempContLeaderCardsTab(GUI gui) {
        super("Temporary Container And Leader Cards");
        tabContent = new VBox ();
        amountBox = new ChoiceBox<> ();
        amountBox.setDisable (true);
        depotBox = new ChoiceBox<> ();

        Rectangle tempContainer = new Rectangle ();
        tempContainer.setStroke (Color.BLACK);
        tempContainer.setFill (Color.rgb (200, 200, 200, 0.5));
        tempContainer.widthProperty ().bind (tabContent.widthProperty ().multiply (0.5));
        tempContainer.heightProperty ().bind (tabContent.heightProperty ().multiply (0.4));
        resources = new HBox ();

        tempContainerStack = new StackPane ();
        tempContainerStack.getChildren ().addAll(tempContainer, resources);

        sendToWarehouse = new Button ("To Warehouse");
        sendToWarehouse.setDisable (true);
        sendToWarehouse.setOnAction (e -> {
            gui.getInterpreter ().addInteraction ("storeOrRemove", "REMOVE");
            gui.getInterpreter ().addInteraction ("depotIdx", String.valueOf (depotBox.getValue ()));
            gui.getInterpreter ().addInteraction ("resource", String.valueOf (depotBox.getValue ()));
        });


        tempContainerHBox = new HBox ();
        tempContainerHBox.setAlignment (Pos.CENTER);
        tempContainerHBox.spacingProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.05));
        tempContainerHBox.getChildren ().addAll (tempContainerStack, amountBox, depotBox, sendToWarehouse);

        leaderCards = new HBox ();
        leaderCards.setAlignment (Pos.CENTER);
        leaderCards.spacingProperty ().bind (JavaFXApp.getFixedWidth ().multiply (0.05));

        tabContent.getChildren ().addAll (tempContainerHBox, leaderCards);
        tabContent.spacingProperty ().bind (JavaFXApp.getFixedHeight ().multiply (0.05));
        tabContent.setAlignment (Pos.CENTER);
        this.setContent (tabContent);
    }

    public HBox getLeaderCards() {
        return leaderCards;
    }

    public VBox getTabContent() {
        return tabContent;
    }

    public StackPane getTempContainerStack() {
        return tempContainerStack;
    }

    public HBox getTempContainerHBox() {
        return tempContainerHBox;
    }

    public ChoiceBox<Integer> getAmountBox() {
        return amountBox;
    }

    public ChoiceBox<Integer> getDepotBox() {
        return depotBox;
    }

    public Button getSendToWarehouse() {
        return sendToWarehouse;
    }

    public HBox getResources() {
        return resources;
    }
}
