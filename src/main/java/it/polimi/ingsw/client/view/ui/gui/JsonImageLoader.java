package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.lightweightmodel.LWResource;
import it.polimi.ingsw.client.view.ui.cli.Colour;
import it.polimi.ingsw.utils.config.JsonHandler;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Objects;

public class JsonImageLoader {
    private final JsonHandler jsonHandler;

    public JsonImageLoader(String pathToDB) {
        jsonHandler = new JsonHandler (pathToDB);
    }

    public Image loadDevCardImage(int ID) {
        String jsonPath = "devCards/";
        try {
            return new Image (Objects.requireNonNull (this.getClass ().getResourceAsStream ((String) jsonHandler.getAsJavaObjectFromJSON (String.class, jsonPath + "ID" + ID))));
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return null;
    }

    public Image loadLeaderCardImage(int ID) {
        String jsonPath = "leaderCards/";
        try {
            return new Image (Objects.requireNonNull (this.getClass ().getResourceAsStream ((String) jsonHandler.getAsJavaObjectFromJSON (String.class, jsonPath + "ID" + ID))));
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return null;
    }

    public Image loadMarketImage() {
        try {
            return new Image (Objects.requireNonNull (this.getClass ().getResourceAsStream ((String) jsonHandler.getAsJavaObjectFromJSON (String.class, "/market/container"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return null;
    }

    public Image loadPersonalBoardImage() {
        try {
            return new Image (Objects.requireNonNull (this.getClass ().getResourceAsStream ((String) jsonHandler.getAsJavaObjectFromJSON (String.class, "/board"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return null;
    }

    public Image loadResourceImage(LWResource resource) {
        String jsonPath = "resources/";
        try {
            return new Image (Objects.requireNonNull (this.getClass ().getResourceAsStream ((String) jsonHandler.getAsJavaObjectFromJSON (String.class, jsonPath + resource.getResourceType ()))));
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return null;
    }
}
