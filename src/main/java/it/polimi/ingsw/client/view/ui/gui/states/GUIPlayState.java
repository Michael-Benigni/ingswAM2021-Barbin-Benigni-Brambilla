package it.polimi.ingsw.client.view.ui.gui.states;

import it.polimi.ingsw.client.view.ui.gui.GUI;
import it.polimi.ingsw.client.view.ui.gui.JavaFXApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
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
            Rectangle2D screenBounds = Screen.getPrimary ().getVisualBounds ();
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


        GridPane grid = new GridPane ();
        Image image = new Image (getClass ().getResourceAsStream ("/images/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png"));
        ImageView imageView = new ImageView (image);
        imageView.fitWidthProperty ().asObject ().bind (JavaFXApp.getFixedWidth ());
        imageView.fitHeightProperty ().asObject ().bind (JavaFXApp.getFixedHeight ());
        /*ColumnConstraints column1 = new ColumnConstraints ();
        column1.setPercentWidth (20);
        grid.getColumnConstraints ().add (column1);
        ColumnConstraints column2 = new ColumnConstraints ();
        column1.setPercentWidth (80);
        grid.getColumnConstraints ().add (column2);

        GridPane paneImages = new GridPane ();
        RowConstraints row1 = new RowConstraints ();
        row1.setPercentHeight (70);
        paneImages.getRowConstraints ().add (row1);
        RowConstraints row2 = new RowConstraints ();
        row2.setPercentHeight (30);
        paneImages.getRowConstraints ().add (row2);

        paneImages.add (imageView, 0, 0);*/
        grid.add (imageView, 3, 1);

        // create a background image
        /*BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition (Side.LEFT, 0, false, Side.BOTTOM, 0, false),
                new BackgroundSize (width / 2, height / 2, false, false, true, true)
        );
        Background background = new Background(backgroundimage);
        BorderPane borderPane = new BorderPane ();
        borderPane.setBackground (background);*/

        //VBox vBox = getTurnsButtonsVBox (width, image);
        //grid.add (vBox, 0, 0);

        Tab tab = new Tab ("Personal Board", grid);
        tab.setClosable (false);
        return tab;
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
        Tab tab = new Tab ("Game Board", grid);
        tab.setClosable (false);
        return tab;
    }
}
