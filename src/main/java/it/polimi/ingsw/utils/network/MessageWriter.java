package it.polimi.ingsw.utils.network;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
            info.remove (nameProperty);
            property = updateProperty (property, gson.toJsonTree (input));
            info.add (nameProperty, property);
        }
        else
            info.add (nameProperty, gson.toJsonTree (input));
    }

    private JsonElement updateProperty(JsonElement element, JsonElement input) {
        int depthProperty = getDeep(element, 0);
        int depthInput = getDeep (input, 0);
        JsonElement result;
        if (depthProperty > depthInput) {
            int depthWhereAdd = depthProperty - depthInput;
            result = addAtDepth (element, input, depthWhereAdd);
        }
        else {
            result = new JsonArray (2);
            result.getAsJsonArray ().add (element);
            result.getAsJsonArray ().add (input);
        }
        return result;
    }

    private JsonElement addAtDepth(JsonElement element, JsonElement toAdd, int depth) {
        JsonElement arrayElement = element.getAsJsonArray ();
        for (int i = 0; i < depth - 1; i++)
            arrayElement = arrayElement.getAsJsonArray ().get (arrayElement.getAsJsonArray ().size () - 1);
        arrayElement.getAsJsonArray ().add (toAdd);
        return element;
    }

    private int getDeep(JsonElement element, int depth) {
        if (element.isJsonArray ()) {
            JsonArray array = element.getAsJsonArray ();
            depth++;
            if (array.size () > 0) {
                JsonElement arrayElem = array.get (0);
                if (arrayElem.isJsonArray ())
                    return getDeep (arrayElem, depth);
            }
        }
        return depth;
    }

    public Sendable write() {
        if (!this.message.has ("header"))
            setHeader (Header.Common.ERROR);
        return gson.fromJson (this.message, Message.class);
    }

    public Object getInfo() {
        return getJsonObjectInfo ();
    }

    private JsonElement getJsonObjectInfo() {
        return message.get ("info");
    }

    public Object getInfo(String subInfo) {
        return getJsonObjectInfo ().getAsJsonObject ().get (subInfo);
    }

    private class Message implements Sendable {
        private JsonElement header;
        private JsonElement info;
    }
}
