package it.polimi.ingsw.model.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.gameresources.markettray.Resource;
import it.polimi.ingsw.model.gameresources.faithtrack.Section;
import it.polimi.ingsw.model.gameresources.faithtrack.Cell;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
    private static final Gson gson = initGson();

    /**
     * This method save a java Object as a Json file
     * @param pathFile path of the file for saving the object
     * @param convertToJson object to save on file as json
     * @throws IOException
     */
    public static void saveAsJsonFile(String pathFile, Object convertToJson) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(pathFile));
        gson.toJson(convertToJson, writer);
        writer.close();
    }

    //TODO: check if is better to have attributeClass or the object attribute
    public static Object getAsJavaObjectFromJSON (Type type, String jsonPath) throws FileNotFoundException {
        return getAsJavaObjectFromJSON(type, jsonPath, PATH_TO_DB);
    }


    public static Object getAsJavaObjectFromJSONArray (Type type, String jsonPath, int[] listIndeces) throws FileNotFoundException {
        return getAsJavaObjectFromJSONArray(type, jsonPath, PATH_TO_DB, listIndeces);
    }


    public static Object getAsJavaObjectFromJSON (Type type, String jsonPath, String filePath) throws FileNotFoundException {
        JsonObject DBAsJsonObject = getFileAsJsonElement(filePath).getAsJsonObject();
        JsonElement attributeValueAsJsonElem = getJsonElement(jsonPath, DBAsJsonObject);
        return gson.fromJson(attributeValueAsJsonElem, type);
    }


    public static Object getAsJavaObjectFromJSONArray (Type type, String jsonPath, String filePath, int[] listIndeces) throws FileNotFoundException {
        JsonObject DBAsJsonObject = getFileAsJsonElement(filePath).getAsJsonObject();
        JsonElement attributeValueAsJsonElem = getJsonElementFromJsonArray(jsonPath, DBAsJsonObject, listIndeces);
        return gson.fromJson(attributeValueAsJsonElem, type);
    }

    private static JsonElement getFileAsJsonElement (String filePath) throws FileNotFoundException {
        return JsonParser.parseReader(new JsonReader(new FileReader(filePath)));
    }

    /**
     * It's the method that actually does the parsing of the JsonFile using the pathToJsonElem
     * @param pathToJsonElem it's a String that represents the path to pass through the Json Tree of the file, stored in
     *                       the JsonTreeStruct
     * @param json it's the whole JsonElement from which the method extracts one of the properties
     * @return the target JsonElement
     */
    //TODO: implementation of getting an element even if there is only a part of the json tree path
    private static JsonElement getJsonElement(String pathToJsonElem, JsonElement json) {
        JsonObject obj;
        JsonElement elem = json;
        ArrayList<String> jsonNodes = ConfigParser.decompose(pathToJsonElem);
        for(int currNode = 0; currNode < jsonNodes.size(); currNode++) {
            obj = elem.getAsJsonObject();
            try {
                elem = obj.get(jsonNodes.get(currNode));
                if (elem.isJsonArray()) {
                    return elem;
                }
            } catch (NullPointerException e) {
                elem = obj;
            }
        }
        return elem;
    }

    private static JsonElement getJsonElementFromJsonArray(String pathToJsonElemInArray, JsonElement json, int[] listIndeces) {
        JsonElement elemOfJsonArray = json;
        for (int i = 0; i < listIndeces.length && pathToJsonElemInArray.length() > 0; i++) {
            JsonArray jsonArray = getJsonElement(pathToJsonElemInArray, elemOfJsonArray).getAsJsonArray();
            elemOfJsonArray = jsonArray.get(listIndeces[i]);
        }
        return elemOfJsonArray;
    }


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
}