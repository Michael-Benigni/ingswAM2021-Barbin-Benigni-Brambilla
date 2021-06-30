package it.polimi.ingsw.utils.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import it.polimi.ingsw.client.view.lightweightmodel.LWFaithPoint;
import it.polimi.ingsw.client.view.lightweightmodel.LWProducible;
import it.polimi.ingsw.client.view.lightweightmodel.LWResource;
import java.lang.reflect.Type;

/**
 * Class that represents the JsonAdapter for the LWResource.
 */
public class LWProducibleAdapter extends JsonAdapter<LWProducible> {
    /**
     * With this method the deserialization looks to a property "className" to understand which is the sublcass of T in
     * which the JsonElement has to be deserialized
     *
     * @param jsonElement                json element to deserialize
     * @param type                       -> of the superclass: every class that inherits from this one will be deserialized with this method
     * @param jsonDeserializationContext -> Gson deserialization context
     * @return the deserialized object
     * @throws JsonParseException
     */
    @Override
    public LWProducible deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonElement amount = jsonElement.getAsJsonObject().get("amount");
        Class<? extends LWProducible> clazz;
        if (amount.isJsonNull ()) {
            clazz = LWFaithPoint.class;
        } else if (amount.isJsonPrimitive ()) {
            clazz = LWResource.class;
        } else {
            throw new IllegalStateException ("Unexpected value: " + amount.getAsString ());
        }
        return jsonDeserializationContext.deserialize (jsonElement, clazz);
    }

    /**
     * This method does a custom serialization: it adds to the Json serialized element the property that indicates the
     * specific sublcass of T of the object to deserialize, and with this the deserialization context will looks to that
     * property and will understand how to deserialize the object
     *
     * @param objectToSerialize        -> the object that will be serialized with this method
     * @param type                     -> type of the object that will be serialized
     * @param jsonSerializationContext -> Gson serialization context
     * @return the serialized Json object
     */
    @Override
    public JsonElement serialize(LWProducible objectToSerialize, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize (objectToSerialize);
    }
}
