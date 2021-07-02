package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.lightweightmodel.LWResource;
import it.polimi.ingsw.client.view.lightweightmodel.LWResourceType;
import it.polimi.ingsw.client.view.moves.PlayMove;
import it.polimi.ingsw.client.view.ui.cli.states.PlayState;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BoardProductionPopup {
    private static ArrayList<Button> btns;

    public static void alert(GUI gui) {
        Stage window = new Stage();
        window.initModality (Modality.APPLICATION_MODAL);
        window.setTitle ("Choose the resource you want to produce");
        window.setMinWidth (250);
        window.setMinHeight (200);

        Label label = new Label ();
        label.setText ("Choose the resource: digit the type between " + PlayState.getAllResourceTypes () + " and the a amount to produce");

        HBox hBox = new HBox (10);
        hBox.setAlignment (Pos.CENTER);
        hBox.setSpacing (10);

        btns = new ArrayList<> ();

        ArrayList<LWResource> resources = new ArrayList<> ();
        for (LWResourceType type : LWResourceType.values ()) {
            resources.add (new LWResource (type, 1));
        }

        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        for (LWResource res : resources) {
            Button powerButton = new Button ("", new ImageView (loader.loadResourceImage (res)));
            TextField amountBox = new TextField ();
            Label amount = new Label ("amount");
            btns.add(powerButton);
            powerButton.setOnAction (e -> {
                gui.getInterpreter ().addInteraction ("produced", res.getResourceType () + " " + amountBox.getText ());
            });
            Button chosen = new Button ("Chosen!");
            chosen.setOnAction (e -> btns.forEach (b -> b.setDisable (true)));
            VBox vBox = new VBox ();
            vBox.setAlignment (Pos.CENTER);
            vBox.getChildren ().addAll (powerButton, amount, amountBox, chosen);
            hBox.getChildren ().add (vBox);
        }

        Button closeButton = new Button ("OK");
        closeButton.setOnAction (e -> {
            window.close ();
        });

        VBox vBox = new VBox ();
        vBox.setAlignment (Pos.CENTER);
        vBox.getChildren ().addAll (hBox, closeButton);
        Scene scene = new Scene (vBox);
        window.setScene (scene);
        window.showAndWait ();
    }
}
