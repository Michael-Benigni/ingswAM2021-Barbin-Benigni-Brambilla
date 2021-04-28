package it.polimi.ingsw.config;

import com.google.gson.*;
import java.lang.reflect.Type;


/**
 * It's a Gson Adapter, used to manage the polymorphism with Json that natively does not support it without adapters
 * for hierarchies of classes. This is a class that provide a standard way of serialization and deserialization in order
 * to make Gson capable to serialize and deserialize objects in a certain manner, to deal the polymorphism.
 * @param <T> -> It's the superclass that it's needed serialize and deserialize, so having on a Json file an element
 *           that could be a subtype of T, with this customized serialization and deserialization it's possible to take
 *           the object with the specific subtype (if the type T is registered witin this Adapter in the Gson that will
 *           be used for serialization and deserialization of T and its subtypes).
 */
public class JsonAdapter<T> implements JsonDeserializer<T>, JsonSerializer<T> {
    private final String CLASS_NAME = "className";


    /**
     * With this method the deserialization looks to a property "className" to understand which is the sublcass of T in
     * which the JsonElement has to be deserialized
     * @param jsonElement json element to deserialize
     * @param type -> of the superclass: every class that inherits from this one will be deserialized with this method
     * @param jsonDeserializationContext -> Gson deserialization context
     * @return the deserialized object
     * @throws JsonParseException
     */
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


    /**
     * This method does a custom serialization: it adds to the Json serialized element the property that indicates the
     * specific sublcass of T of the object to deserialize, and with this the deserialization context will looks to that
     * property and will understand how to deserialize the object
     * @param objectToSerialize -> the object that will be serialized with this method
     * @param type -> type of the object that will be serialized
     * @param jsonSerializationContext -> Gson serialization context
     * @return the serialized Json object
     */
    @Override
    public JsonElement serialize(T objectToSerialize, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement el = null;
        el = jsonSerializationContext.serialize(objectToSerialize, objectToSerialize.getClass());
        el.getAsJsonObject().addProperty(CLASS_NAME, objectToSerialize.getClass().getName());
        return el;
    }
}