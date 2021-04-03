package it.polimi.ingsw.model.gameresources;

import com.google.gson.*;
import it.polimi.ingsw.model.personalboard.StorableResource;
import java.lang.reflect.Type;

public class ResourceAdapter implements JsonDeserializer<Resource>, JsonSerializer<Resource> {
    //private static Map<Class, Resource> classResourceMap;

    @Override
    public Resource deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Resource resource = null;
        JsonObject obj = jsonElement.getAsJsonObject();
        if (obj.has("resourceType"))
            resource = jsonDeserializationContext.deserialize(jsonElement, StorableResource.class);
        if(obj.has("points"))
            resource = jsonDeserializationContext.deserialize(jsonElement, FaithPoint.class);
        return resource;
    }

    @Override
    public JsonElement serialize(Resource resource, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement el = null;
        /*
        if (resource.getClass().equals(StorableResource.class))
            el = jsonSerializationContext.serialize(resource, StorableResource.class);
        if (resource.getClass().equals(FaithPoint.class))
            el = jsonSerializationContext.serialize(resource, FaithPoint.class);

         */
        el = jsonSerializationContext.serialize(resource, resource.getClass());

        return el;
    }
}
