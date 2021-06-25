package it.polimi.ingsw.client;

import java.io.FileNotFoundException;

import static it.polimi.ingsw.utils.config.JsonHandler.getAsJavaObjectFromJSON;

public class ClientPrefs {
    private static final String PATH_TO_PREFS = "json/clientPrefs.json";
    private static String pathToDB;
    private static long timeToWaitConnection;

    public static String getPathToDB() {
        try {
            pathToDB = (String) getAsJavaObjectFromJSON(String.class, "pathToDB/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return pathToDB;
    }

    public static long getTimeToWaitConnection() {
        try {
            timeToWaitConnection = (long) getAsJavaObjectFromJSON(long.class, "timeToWaitConnection/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return timeToWaitConnection;
    }
}
