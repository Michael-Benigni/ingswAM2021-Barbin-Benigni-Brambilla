package it.polimi.ingsw.model.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.gameresources.markettray.Resource;
import it.polimi.ingsw.model.gameresources.faithtrack.Section;
import it.polimi.ingsw.model.gameresources.faithtrack.Cell;
import java.io.*;

//TODO: check doc
/**
 * It's the class that creates the objects requested from other parts of the project reading a json file, and it can also
 * write a json file to save objects' status
 */
public class ConfigLoaderWriter {
    /**
     * It's the path of the main database with all the objects needed for the game
     */
    private static final String PATH_TO_DB = "config/db.json";


    /**
     * This method initializes the Gson object with all the custom serializations and deserializations for parsing a file
     * of the game. A Gson of this type is instantiated every time an object of the model want to load or save data.
     * @return Gson with custom settings
     */
    private static Gson initGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        registerAllTypeAdapters(gsonBuilder);
        return gsonBuilder.create();
    }


    /**
     * This method registers all the necessary adapters to parse the database of the project to manage polymorphism,
     * because gson is not able to understand which is the correct instance of an interface or of an abstract class
     * @param gsonBuilder is the object that allows to create a gson with custom settings
     */
    private static void registerAllTypeAdapters(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(Resource.class, new JsonAdapter<Resource>());
        gsonBuilder.registerTypeAdapter(Cell.class, new JsonAdapter<Cell>());
        gsonBuilder.registerTypeAdapter(Section.class, new JsonAdapter<Section>());
    }

    private static JsonElement getFileAsJsonElement (String filePath) throws FileNotFoundException {
        Gson gson = initGson();
        return JsonParser.parseReader(new JsonReader(new FileReader(filePath)));
    }

    //TODO: check if is better to have attributeClass or the object attribute
    public static Object getAttribute(Object attributeTofill, String propertyKey, String JsonObjectKey) throws FileNotFoundException {
        JsonObject DBAsJsonObject = getFileAsJsonElement(PATH_TO_DB).getAsJsonObject();
        JsonObject classParameters = DBAsJsonObject.get(JsonObjectKey).getAsJsonObject();
        JsonElement attributeAsJsonObject = classParameters.get(propertyKey);
        Gson gson = initGson();
        return gson.fromJson(attributeAsJsonObject, attributeTofill.getClass());
    }

    static Object getAttribute(Object attributeTofill, String attributeName, Class ownerOfAttribute, String filePath) throws FileNotFoundException {
        JsonObject DBAsJsonObject = getFileAsJsonElement(filePath).getAsJsonObject();
        JsonObject classParameters = DBAsJsonObject.get(ownerOfAttribute.getSimpleName()).getAsJsonObject();
        JsonElement attributeAsJsonObject = classParameters.get(attributeName);
        Gson gson = initGson();
        return gson.fromJson(attributeAsJsonObject, attributeTofill.getClass());
    }

    /**
     * This method save a java Object as a Json file
     * @param pathFile path of the file for saving the object
     * @param convertToJson object to save on file as json
     * @throws IOException
     */
    public static void saveAsJsonFile(String pathFile, Object convertToJson) throws IOException {
        Gson gson = initGson();
        Writer writer = new BufferedWriter(new FileWriter(pathFile));
        gson.toJson(convertToJson, writer);
        writer.close();
    }


    /**
     * This method receive the Type of the target object, and through the type it finds the correct path mapped in the
     * JsonTreeStruct. This path is necessary to correctly parse the Json database. This method is the public interface, and
     * it hides the struct of the Json Tree, calling the method that actually does the parsing
     * @param T is the type of the object that the caller wants to read from the database
     * @return the Object that the caller want to load from the main database
     * @throws FileNotFoundException if the file does not exist
     *
    public static Object getJavaObjectFromMainDB(Type T) throws FileNotFoundException {
        return getJavaObjectFromFile(PATH_TO_DB, T);
    }*/


    /**
     * It's the method that actually does the creation of the Object that is saved in a Json file, after creating
     * the correct Gson, with custom settings to correctly parse a Json file of the project. It does the conversion from
     * the JsonElement, returned from Gson after parsing, to a java Object.
     * @param filePath it's the path of the file that the caller want to load from
     * @param T the type of the Object that the caller expects
     * @return the Object that the caller want to load from the Json file
     * @throws FileNotFoundException if the file does not exist
     *
    public static Object getJavaObjectFromFile(String filePath, Type T) throws FileNotFoundException {
        Gson gson = initGson();
        JsonElement fileAsJson = JsonParser.parseReader(new JsonReader(new FileReader(filePath)));
        JsonElement elem = null;
        try {
            String pathJsonTree = JsonTreeStruct.getPath(T.getTypeName());
            elem = getJsonElement(pathJsonTree, fileAsJson);
        } catch (NoPathFoundException e) {
            elem = fileAsJson;
        } finally {
            return gson.fromJson(elem, T);
        }
    }*/


    /**
     * It's the method that actually does the parsing of the JsonFile using the pathToJsonElem
     * @param pathToJsonElem it's a String that represents the path to pass through the Json Tree of the file, stored in
     *                       the JsonTreeStruct
     * @param json it's the whole JsonElement from which the method extracts one of the properties
     * @return the target JsonElement
     *
    //TODO: implementation of getting an element even if there is only a part of the json tree path
    private static JsonElement getJsonElement(String pathToJsonElem, JsonElement json) {
        JsonObject obj;
        JsonElement elem = json;
        List jsonNodes = ConfigParser.decompose(pathToJsonElem);
        for(int i = 0; i < jsonNodes.size(); i++) {
            obj = elem.getAsJsonObject();
            elem = obj.get((String) jsonNodes.get(i));
        }
        return elem;
    }*/
}

/**
 * It's the class that represents the actual structure of the json database: it contains all the information to parse and
 * read the database
 *
class JsonTreeStruct {
    private static final HashMap<String, String> JSON_PATHS = initMap();*/

    /**
     * This method initializes the map of classes names and the actual path to reach objects of that type in the database
     * @return the initialized map
     *
    private static HashMap<String, String> initMap() {
        HashMap<String, String> map = new HashMap<>();
        //TODO: set paths
        map.put(MarketMarble.class.getName(), "");
        return map;
    }*/

    /**
     * This method returns the correct path of the Json nodes to pass through the database, that is a json file
     * @param className it's the class of the object that is reached with the path returned
     * @return it's a String that represents the series of the json nodes to pass through
     *
    static String getPath(String className) throws NoPathFoundException {
        if(JSON_PATHS.get(className) != null)
            return JSON_PATHS.get(className);
        throw new NoPathFoundException();
    }
}*/