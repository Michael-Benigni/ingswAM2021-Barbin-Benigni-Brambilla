package it.polimi.ingsw.utils.network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MessageWriter {
    private JsonObject message;
    private final Gson gson;

    public MessageWriter() {
        this.message = new JsonObject ();
        this.message.add ("info", new JsonObject ());
        this.gson = new Gson ();
    }

    public void setHeader(Header header) {
        message.addProperty ("header", String.valueOf (header));
    }

    public void reset() {
        this.message = new JsonObject ();
    }

    public void addProperty(String nameProperty, Object input) {
        JsonObject info = message.get("info").getAsJsonObject ();
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
        if (!this.message.has ("header"))
            setHeader (Header.Common.ERROR);
        return gson.fromJson (this.message, Message.class);
    }

    private class Message implements Sendable {
        private JsonElement header;
        private JsonElement info;
    }
}
