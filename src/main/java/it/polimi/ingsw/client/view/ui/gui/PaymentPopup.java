package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.lightweightmodel.LWResource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PaymentPopup {
    public static Integer howMuchPay(LWResource resource) {
        Stage window = new Stage();
        window.initModality (Modality.APPLICATION_MODAL);
        window.setTitle ("Payment");
        window.setMinWidth (250);
        window.setMinHeight (200);

        Label label = new Label ();
        label.setText ("Choose the amount to pay of the resource of type " + resource.getResourceType () + " selected:");
        ChoiceBox<Integer> amountBox = new ChoiceBox<> ();
        ArrayList<Integer> amounts = new ArrayList<> ();
        for (int i = 1; i <= resource.getAmount (); i++)
            amounts.add (i);
        amountBox.getItems ().addAll (amounts);
        amountBox.setValue (resource.getAmount ());
        Button closeButton = new Button ("OK");
        closeButton.setOnAction (e -> {
            window.close ();
        });

        VBox vBox = new VBox (10);
        vBox.getChildren ().addAll (label, amountBox, closeButton);
        vBox.setAlignment (Pos.CENTER);

        Scene scene = new Scene (vBox);
        window.setScene (scene);
        window.showAndWait ();
        return amountBox.getValue ();
    }
}
