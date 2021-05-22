package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.Sendable;

public class MessageWriter {
    private JsonObject object;
    private final Gson gson;

    public MessageWriter() {
        this.object = new JsonObject ();
        this.object.add ("info", new JsonObject ());
        this.gson = new Gson ();
    }

    public void setHeader(Header header) {
        object.addProperty ("header", String.valueOf (header));
    }

    public void reset() {
        this.object = new JsonObject ();
    }

    public void addProperty(String nameProperty, Object input) {
        JsonObject info = object.get("info").getAsJsonObject ();
        if (info.has (nameProperty)) {
            JsonElement property = info.get (nameProperty);
            if (property.isJsonArray ())
                property.getAsJsonArray ().add (gson.toJsonTree (input));
            else {
                info.remove (nameProperty);
                Object[] objects = new Object[] {property, input};
                info.add (nameProperty, gson.toJsonTree (objects));
            }
        }
        info.add (nameProperty, gson.toJsonTree (input));
    }

    public Sendable write() {
        if (!this.object.has ("header"))
            setHeader (Header.Common.ERROR);
        return gson.fromJson (this.object, Message.class);
    }

    private class Message implements Sendable {
        private JsonElement header;
        private JsonElement info;
    }
}
