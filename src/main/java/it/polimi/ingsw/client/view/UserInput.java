package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.JsonTransmittable;
import java.util.List;

public class UserInput implements JsonTransmittable{
    // TODO: replace JsonObject with UserInput
    private JsonObject object;
    private final Gson gson;

    public UserInput() {
        this.object = new JsonObject ();
        this.gson = new Gson ();
    }

    public void addProperty(String property, List<Object> list) {
        JsonElement listAsJSON = gson.toJsonTree (list);
        object.add (property, listAsJSON);
    }

    public void addProperty(String property, UserInput userInput) {
        //TODO: when there is already the property -----> use a List
        object.add (property, gson.toJsonTree (userInput));
    }

    public void addProperty(String property, Number number) {
        object.addProperty (property, number);
    }

    public void addProperty(String property, String value) {
        object.addProperty (property, value);
    }

    public void setHeader(Header header) {
        object.addProperty ("header", String.valueOf (header));
    }

    public void reset() {
        this.object = new JsonObject ();
    }

    public void addProperty(String nameProperty, Object input) {
        object.add (nameProperty, gson.toJsonTree (input));
    }
}
