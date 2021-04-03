package it.polimi.ingsw.model.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.gameboard.markettray.MarketMarble;
import it.polimi.ingsw.model.gameresources.Resource;
import it.polimi.ingsw.model.gameresources.ResourceAdapter;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * It's the class that creates the objects requested from other parts of the project reading and parsing the json database
 */
public class ConfigLoader {
    /**
     * It's the path of the main database with all the objects needed for the game
     */
    private static final String PATH_TO_DB = "config/db.json";

    /**
     * This method initializes the Gson object with all the custom serializations and deserializations for parsing the
     * database of the game. A gson of this type is instantiated every time an object of the model want to load data.
     * @return Gson with custom settings
     * @throws FileNotFoundException
     */
    private static Gson initGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        registerAllTypeAdapters(gsonBuilder);
        return gsonBuilder.create();
    }

    /**
     * This method registers all the necessary adapters to parse the database of the project to manage polymorphism,
     * because gson is not able to understand which is the correct instance of an interface or of an abstract class
     * @param gsonBuilder is the object that allows to create a gson with custom settings
     */
    private static void registerAllTypeAdapters(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(Resource.class, new ResourceAdapter());
    }

    /**
     * This method receive the Type of the target object, and through the type it finds the correct path mapped in the
     * JsonTreeStruct. This path is necessary to correctly parse the Json file. This method is the public interface, and
     * it hides the struct of the Json Tree, calling the method that actually does the parsing
     * @param T is the type of the object that the caller wants to read from the database
     * @return the Object that the caller want to load from the Json file
     * @throws FileNotFoundException if the file does not exist
     */
    public static Object getJavaObject(Type T) throws FileNotFoundException {
        return getJavaObjectFromPath(JsonTreeStruc.getPath(T.getTypeName()), T);
    }

    /**
     * It's the method that actually does the creation of the Object that is saved in the Json database, after creating
     * the correct Gson, with custom settings to correctly parse the Json file. It does the conversion from the
     * JsonElement returned from gson after the parsing to a java Object.
     * @param pathJsonTree it's a String that represents the path to pass through the Json Tree of the file, stored in
     *                     the JsonTreeStruct
     * @param T the type of the Object that the caller expects
     * @return the Object that the caller want to load from the Json file
     * @throws FileNotFoundException if the file does not exist
     */
    private static Object getJavaObjectFromPath(String pathJsonTree, Type T) throws FileNotFoundException {
        Gson gson = initGson();
        JsonElement fileAsJson = JsonParser.parseReader(new JsonReader(new FileReader(PATH_TO_DB)));
        JsonElement elem = getJsonElement(pathJsonTree, fileAsJson);
        return gson.fromJson(elem, T);
    }

    /**
     * It's the method that actually does the parsing of the JsonFile using the pathToJsonElem
     * @param pathToJsonElem it's a String that represents the path to pass through the Json Tree of the file, stored in
     *      *                     the JsonTreeStruct
     * @param json it's the whole JsonElement from which the method extracts one of the properties
     * @return the target JsonElement
     */
    private static JsonElement getJsonElement(String pathToJsonElem, JsonElement json) {
        JsonObject obj;
        JsonElement elem = json;
        List jsonNodes = ConfigParser.decompose(pathToJsonElem);
        for(int i = 0; i < jsonNodes.size(); i++) {
            obj = elem.getAsJsonObject();
            elem = obj.get((String) jsonNodes.get(i));
        }
        return elem;
    }
}

/**
 * It's the class that represents the actual structure of the json database: it contains all the information to parse and
 * read the database
 */
class JsonTreeStruc {
    private static final HashMap<String, String> JSON_PATHS = initMap();

    /**
     * This method initializes the map of classes names and the actual path to reach objects of that type in the database
     * @return the initialized map
     */
    private static HashMap<String, String> initMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MarketMarble.class.getName(), "marbles/marble");
        return map;
    }

    /**
     * This method returns the correct path of the Json nodes to pass through the database, that is a json file
     * @param className it's the class of the object that is reached with the path returned
     * @return it's a String that represents the series of the json nodes to pass through
     */
    static String getPath(String className) {
        return JSON_PATHS.get(className);
    }
}