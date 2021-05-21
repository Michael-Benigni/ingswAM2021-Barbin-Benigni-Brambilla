package it.polimi.ingsw.utils.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.server.model.cards.leadercards.Requirement;
import it.polimi.ingsw.server.model.gameresources.Producible;
import it.polimi.ingsw.server.model.gameresources.Resource;
import it.polimi.ingsw.server.model.gameresources.faithtrack.Cell;
import it.polimi.ingsw.server.model.gameresources.faithtrack.Section;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * It's the class that creates the objects requested from other parts of the project reading a json file, and it can also
 * write a json file to save objects' status
 */
public class JsonHandler {

    /**
     * It's the path of the main database with all the objects needed for the game
     */
    private final String PATH_TO_DB;

    /**
     * it's the object provided by the Gson library to serialize and deserialize Java objects into and from Json objects
     */
    private static final Gson gson = initGson();


    public JsonHandler(String pathToFile) {
        this.PATH_TO_DB = pathToFile;
    }


    /**
     * This method save a java Object as a Json file. If the file already exists, it will be overwritten, otherwise if
     * there is not a file correspondent to pathFile, then a new file with pathFile as path will be created and written
     * @param pathFile path of the file for saving the Java object as Json file
     * @param convertToJson object to save on file as json
     * @throws IOException if something goes wrong in the opening of the stream
     */
    public static void saveAsJsonFile(String pathFile, Object convertToJson) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(pathFile));
        gson.toJson(convertToJson, writer);
        writer.close();
    }

    public static Object fromJson(String jsonString, Type type) {
        return gson.fromJson(jsonString, type);
    }

    public static String asJson(Object object) {
        return gson.toJson (object);
    }


    /**
     *
     * @param type is the class of the object that will be deserialized
     * @param jsonPath is a string that represents the json keys to access to obtain the desired object: with this path
     *                 the JsonHandler knows always what property it have to access
     * @return the deserialized Json object, as Java object
     * @throws FileNotFoundException if the loading file does not exist
     */
    public Object getAsJavaObjectFromJSON (Type type, String jsonPath) throws FileNotFoundException {
        return getAsJavaObjectFromJSON(type, jsonPath, PATH_TO_DB);
    }


    /**
     * It's the method that deserializes a Json object that belongs to a Json array, or that belongs to an element that
     * can be reached only passing through at least one Json Array (or more). The path of the Json file is set to the main DB
     * @param type the type of the element that will be deserialized
     * @param jsonPath string that represents the sequences of the Json nodes of the Json tree in the Json file
     * @param listIndeces it's an ordered array of indeces: the length of this array represents the number of Json arrays
     *                    that will be crossed to reach the target element, and each element represent the index of the
     *                    correspondent array. For example if listIndeces is [2,6], means that while the Json tree is
     *                    crossed, when is found the first Json array the 2nd element will be access, and when is reached
     *                    the third array, the 6th element will be accessed.
     * @return the correspondent Java Object, result of the deserialization of the Json element of the file
     * @throws FileNotFoundException
     */
    public Object getAsJavaObjectFromJSONArray (Type type, String jsonPath, int[] listIndeces) throws FileNotFoundException {
        return getAsJavaObjectFromJSONArray(type, jsonPath, PATH_TO_DB, listIndeces);
    }


    /**
     * It's the method that actually does the parsing of the JsonFile using the pathToJsonElem. If during the crossing of
     * the Json tree it is reached a Json Array, the method returns the array because this method has no way to know which
     * element of the array is requested, since there is not indeces passed as parameters.
     * @param jsonPath it's a String that represents the path to pass through the Json Tree of the file, stored in
     *                       the JsonTreeStruct
     * @param filePath it's the file from which the element is deserialized
     * @return the target JsonElement as Java Object
     */
    public static Object getAsJavaObjectFromJSON (Type type, String jsonPath, String filePath) throws FileNotFoundException {
        JsonObject DBAsJsonObject = getFileAsJsonElement(filePath).getAsJsonObject();
        JsonElement attributeValueAsJsonElem = getJsonElement(jsonPath, DBAsJsonObject);
        return gson.fromJson(attributeValueAsJsonElem, type);
    }


    /**
     * It's the method that actually does the parsing of the JsonFile using the pathToJsonElem. If during the crossing of
     * the Json tree it is reached a Json Array, the method returns the array because this method has no way to know which
     * element of the array is requested, since there is not indeces passed as parameters.
     * @param jsonPath it's a String that represents the path to pass through the Json Tree of the file, stored in
     *                       the JsonTreeStruct
     * @param type the type of the element that will be deserialized
     * @param jsonString it's the string in Json from which the element is deserialized
     * @return the target JsonElement as Java Object
     */
    public static Object getAsJavaObjectFromJSONStr(Type type, String jsonPath, String jsonString) {
        JsonElement element = JsonParser.parseString (jsonString);
        return gson.fromJson (getJsonElement (jsonPath, element), type);
    }


    /**
     * It's the method that deserializes a Json object that belongs to a Json array, or that belongs to an element that
     * can be reached only passing through at least one Json Array (or more).
     * @param type the type of the element that will be deserialized
     * @param jsonPath string that represents the sequences of the Json nodes of the Json tree in the Json file
     * @param listIndeces it's an ordered array of indeces: the length of this array represents the number of Json arrays
     *                    that will be crossed to reach the target element, and each element represent the index of the
     *                    correspondent array. For example if listIndeces is [2,6], means that while the Json tree is
     *                    crossed, when is found the first Json array the 2nd element will be access, and when is reached
     *                    the third array, the 6th element will be accessed.
     * @param filePath the path of the Json file that will be read
     * @return the correspondent Java Object, result of the deserialization of the Json element of the file
     * @throws FileNotFoundException
     */
    public static Object getAsJavaObjectFromJSONArray (Type type, String jsonPath, String filePath, int[] listIndeces) throws FileNotFoundException {
        JsonObject DBAsJsonObject = getFileAsJsonElement(filePath).getAsJsonObject();
        JsonElement attributeValueAsJsonElem = getJsonElementFromJsonArray(jsonPath, DBAsJsonObject, listIndeces);
        return gson.fromJson(attributeValueAsJsonElem, type);
    }


    /**
     * this method get the whole file as a JsonElement
     * @param filePath the path of the Json file
     * @return the file as JsonElement
     * @throws FileNotFoundException if the file does not exist
     */
    private static JsonElement getFileAsJsonElement(String filePath) throws FileNotFoundException {
        return JsonParser.parseReader(new JsonReader(new FileReader(filePath)));
    }


    /**
     * It's the method that actually does the crossing of the JsonElement. If during the crossing of the Json tree it is
     * reached a Json Array, the method returns the whole JsonArray because this method has no way to know which
     * element of the array is requested, since there is not indeces passed as parameters.
     * @param pathToJsonElem it's a String that represents the path to pass through the Json Tree of the JsonElement json
     * @param json it's the whole JsonElement from which the method extracts one of the properties
     * @return the target JsonElement
     */
    private static JsonElement getJsonElement(String pathToJsonElem, JsonElement json) {
        JsonObject obj;
        JsonElement elem = json;
        ArrayList<String> jsonNodes = StringParser.decompose(pathToJsonElem);
        for(int currNode = 0; jsonNodes.size() > 0; ) {
            obj = elem.getAsJsonObject();
            try {
                elem = obj.get(jsonNodes.get(currNode));
                if (elem.isJsonArray()) {
                    return elem;
                }
            } catch (NullPointerException e) {
                elem = obj;
            }
            jsonNodes.remove(currNode);
            pathToJsonElem = StringParser.compose(jsonNodes);
            jsonNodes = StringParser.decompose(pathToJsonElem);
        }
        return elem;
    }


    /**
     * It's the method that get a Json object that belongs to a Json array, or that belongs to an element that
     * can be reached only passing through at least one Json Array (or more).
     * @param pathToJsonElemInArray string that represents the sequences of the Json nodes of the Json tree of json param
     * @param listIndeces it's an ordered array of indeces: the length of this array represents the number of Json arrays
     *                    that will be crossed to reach the target element, and each element represent the index of the
     *                    correspondent array. For example if listIndeces is [2,6], means that while the Json tree is
     *                    crossed, when is found the first Json array the 2nd element will be access, and when is reached
     *                    the third array, the 6th element will be accessed.
     * @param json the JsonElement to cross
     * @return the correspondent JsonElement at the path pathToJsonElemInArray
     */
    private static JsonElement getJsonElementFromJsonArray(String pathToJsonElemInArray, JsonElement json, int[] listIndeces) {
        JsonElement element = json;
        for (int i = 0; pathToJsonElemInArray != null; i++) {
            element = getJsonElement(pathToJsonElemInArray, element);
            if(element.isJsonArray() && i < element.getAsJsonArray().size()) {
                element = element.getAsJsonArray().get(listIndeces[i]);
            }
            pathToJsonElemInArray = updateJsonPath(element, pathToJsonElemInArray);
        }
        return element;
    }


    /**
     * It's the method that updates the String that represents the JsonTree path: is used in the method getJsonElementFromArray:
     * every time is reached a JsonArray, the path to reach that array is deleted from the string "path" and returned to
     * the caller. This mechanism is used to allow the method getJsonElementFromJsonArray when has to stop
     * @param element
     * @param path
     * @return
     */
    private static String updateJsonPath(JsonElement element, String path) {
        ArrayList<String> listNodes = StringParser.decompose(path);
        int firstNode = 0;
        listNodes.remove(firstNode);
        while (listNodes.size() > 0) {
            String node = listNodes.get(firstNode);
            if (element.isJsonPrimitive() || element.getAsJsonObject().has(node)) {
                if (element.isJsonPrimitive())
                    return null;
                return StringParser.compose(listNodes);
            }
            listNodes.remove(node);
        }
        return null;
    }


    /**
     * This method initializes the Gson object with all the custom serializations and deserializations for parsing a file
     * of the game. It's the method that initialize the Gson attribute of this class
     * @return Gson with custom settings
     */
    private static Gson initGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setPrettyPrinting();
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
        gsonBuilder.registerTypeAdapter(Requirement.class, new JsonAdapter<Requirement>());
        gsonBuilder.registerTypeAdapter(Producible.class, new JsonAdapter<Producible>());
    }
}
