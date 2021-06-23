package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.gui.GUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;

public class GUIPlayState extends GUIState {
    private static Scene scene;

    @Override
    protected void setScene(Scene scene) {
        GUIPlayState.scene = scene;
    }

    @Override
    public GUIState getNextState() {
        return new GUIGameOverState();
    }

    @Override
    public Scene buildScene(GUI gui) {
        if (getScene () == null) {
            TabPane tabPane = new TabPane ();
            Rectangle2D screenBounds = Screen.getPrimary ().getBounds ();
            Tab tabPersonalBoard = getPersonalBoardTab (screenBounds.getWidth (), screenBounds.getHeight ());
            Tab tabGameBoard = getGameBoardTab (screenBounds.getWidth (), screenBounds.getHeight ());
            tabPane.getTabs ().addAll (tabGameBoard, tabPersonalBoard);
            setScene (new Scene (tabPane));
        }
        return getScene ();
    }

    @Override
    protected Scene getScene() {
        return scene;
    }

    private Tab getPersonalBoardTab(double width, double height) {
        Image image = new Image (getClass ().getResourceAsStream ("/images/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png"), width, height, true, false);

        // create a background image
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundimage);

        BorderPane borderPane = new BorderPane ();
        borderPane.setBackground (background);

        VBox vBox = getTurnsButtonsVBox (width, image);
        borderPane.setLeft (vBox);

        return new Tab ("Personal Board", borderPane);
    }

    private VBox getTurnsButtonsVBox(double width, Image image) {
        Button marketTurn = new Button ("Market");
        marketTurn.setMaxWidth (Double.MAX_VALUE);
        marketTurn.setOnAction (e -> {});

        Button buyCardTurn = new Button ("Buy Card");
        buyCardTurn.setMaxWidth (Double.MAX_VALUE);
        buyCardTurn.setOnAction (e -> {});

        Button productionTurn = new Button ("Production");
        productionTurn.setMaxWidth (Double.MAX_VALUE);
        productionTurn.setOnAction (e -> {});

        Label buttonsLabel = new Label ("Choose \nyour \nTurn \nType!");
        buttonsLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        VBox buttons = new VBox (10);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(20, 20, 10, 20));
        buttons.setBackground (new Background (new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
        buttons.setPrefWidth ((width - image.getWidth ()) / 2);
        buttons.setAlignment (Pos.TOP_CENTER);
        buttons.getChildren ().addAll (buttonsLabel, marketTurn, buyCardTurn, productionTurn);

        return buttons;
    }

    private Tab getGameBoardTab(double width, double height) {
        Image image = new Image (getClass ().getResourceAsStream ("/images/punchboard/plancia portabiglie.png"), width, height, true, false);
        ImageView view = new ImageView (image);
        GridPane grid = new GridPane ();
        grid.setGridLinesVisible (true);
        grid.setHgap (5);
        grid.setHgap (4);
        Background background = new Background (new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY));
        grid.setBackground (background);
        grid.add (view, 0, 0);
        return new Tab ("Game Board", grid);
    }
}
