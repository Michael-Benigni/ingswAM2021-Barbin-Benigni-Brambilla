package it.polimi.ingsw.client.view.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Popup {

    public static void alert(String title, String info) {
        Stage window = new Stage ();
        window.initModality (Modality.APPLICATION_MODAL);
        window.setTitle (title);
        window.setMinWidth (250);
        window.setMinHeight (200);

        Label label = new Label ();
        label.setText (info);
        Button closeButton = new Button ("OK");
        closeButton.setOnAction (e -> window.close ());

        VBox vBox = new VBox (10);
        vBox.getChildren ().addAll (label, closeButton);
        vBox.setAlignment (Pos.CENTER);

        Scene scene = new Scene (vBox);
        window.setScene (scene);
        window.showAndWait ();
    }
}
