package it.polimi.ingsw.server.utils.config;

import it.polimi.ingsw.server.utils.config.JsonHandler;

import java.io.FileNotFoundException;

public class Prefs {
    private static final String PATH_TO_PREFS = "src/main/resources/prefs.json";
    private static int serverPort;
    private static int timerPeriod;
    private static String pathServerDB;
    private static int maxUsersInWaitingRoom;


    public static void load() throws FileNotFoundException {
        serverPort = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, "serverPort/", PATH_TO_PREFS);
        timerPeriod = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, "heartbeatPeriod/", PATH_TO_PREFS);
        pathServerDB = (String) JsonHandler.getAsJavaObjectFromJSON(String.class, "pathMainServerDB/", PATH_TO_PREFS);
        maxUsersInWaitingRoom = (int) JsonHandler.getAsJavaObjectFromJSON(int.class, "maxUsersInWaitingRoom/", PATH_TO_PREFS);
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static int getTimerPeriod() {
        return timerPeriod;
    }

    public static String getDBPath() {
        return pathServerDB;
    }

    public static int getMaxUsersInWaitingRoom() {
        return maxUsersInWaitingRoom;
    }
}
