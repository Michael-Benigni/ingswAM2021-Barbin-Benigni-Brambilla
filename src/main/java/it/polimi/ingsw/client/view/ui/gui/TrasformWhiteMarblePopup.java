package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.lightweightmodel.LWWMPower;
import it.polimi.ingsw.client.view.moves.PlayMove;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;

public class TrasformWhiteMarblePopup {

    public static void alert(ArrayList<LWWMPower> whiteMarblePowers, GUI gui) {
        Stage window = new Stage();
        window.initModality (Modality.APPLICATION_MODAL);
        window.setTitle ("Payment");
        window.setMinWidth (250);
        window.setMinHeight (200);

        Label label = new Label ();
        label.setText ("Choose among your active powers:");

        HBox hBox = new HBox (10);
        hBox.setAlignment (Pos.CENTER);
        hBox.setSpacing (10);

        JsonImageLoader loader = new JsonImageLoader (ClientPrefs.getPathToDB ());
        for (LWWMPower power : whiteMarblePowers) {
            Button powerButton = new Button ("", new ImageView (loader.loadResourceImage (power.getResourceObtained ())));
            powerButton.setOnAction (e -> {
                gui.getInterpreter ().addInteraction ("resourceIdx", String.valueOf (power.getPowerWMIndex ()));
                new MoveService (PlayMove.WHITE_MARBLE.getMove (), gui).start ();
            });
            hBox.getChildren ().add (powerButton);
        }

        Scene scene = new Scene (hBox);
        window.setScene (scene);
        window.showAndWait ();
    }
}
