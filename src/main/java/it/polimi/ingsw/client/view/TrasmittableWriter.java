package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.JsonTrasmittable;
import java.util.List;

public class TrasmittableWriter {
    private JsonObject object;
    private Gson gson;

    public TrasmittableWriter() {
        this.object = new JsonObject ();
        this.gson = new Gson ();
    }

    public void addList(String property, List<Object> list) {
        JsonElement listAsJSON = gson.toJsonTree (list);
        object.add (property, listAsJSON);
    }

    public void addNumber(String property, Number number) {
        object.addProperty (property, number);
    }

    public void addString(String property, String value) {
        object.addProperty (property, value);
    }

    public void setHeader(Header header) {
        object.addProperty ("header", String.valueOf (header));
    }

    public void reset() {
        this.object = new JsonObject ();
    }

    public JsonTrasmittable write() {
        return new JsonTrasmittable () {
            @Override
            public String transmit() {
                return gson.toJson (object);
            }
        };
    }
}
