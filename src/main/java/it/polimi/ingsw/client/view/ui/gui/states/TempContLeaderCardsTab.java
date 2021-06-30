package it.polimi.ingsw.client.view.ui.gui.states;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TempContLeaderCardsTab extends Tab {
    private final HBox leaderCards;

    public TempContLeaderCardsTab() {
        super("Temporary Container And Leader Cards");
        VBox tabContent = new VBox ();
        Canvas canvas = new Canvas ();
        //canvas.widthProperty ().bind ();
        leaderCards = new HBox ();

    }
}
