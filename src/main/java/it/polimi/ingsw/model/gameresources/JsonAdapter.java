package it.polimi.ingsw.model.gameresources;

import com.google.gson.*;
import java.lang.reflect.Type;

public class JsonAdapter<T> implements JsonDeserializer<T>, JsonSerializer<T> {
    private final String CLASS_NAME = "className";

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        T deserializedObject = null;
        JsonElement javaType = jsonElement.getAsJsonObject().get(CLASS_NAME);
        try {
            Class<?> clazz =  Class.forName(javaType.getAsString());
            deserializedObject = jsonDeserializationContext.deserialize(jsonElement, clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deserializedObject;
    }

    @Override
    public JsonElement serialize(T objectToSerialize, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement el = null;
        el = jsonSerializationContext.serialize(objectToSerialize, objectToSerialize.getClass());
        el.getAsJsonObject().addProperty(CLASS_NAME, objectToSerialize.getClass().getName());
        return el;
    }
}