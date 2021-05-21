package it.polimi.ingsw.utils.config;

import java.io.FileNotFoundException;
import static it.polimi.ingsw.utils.config.JsonHandler.getAsJavaObjectFromJSON;

public class Prefs {
    private static final String PATH_TO_PREFS = "src/main/resources/prefs.json";
    private static int serverPort;
    private static int timerPeriod;
    private static String pathServerDB;
    private static int maxUsersInWaitingRoom;
    private static long maxTimeForACKResponseMSec;
    private static String defaultServerIP;
    private static String defaultUICmd;
    private static int maxNumOfPlayers;

    public static int getServerPort() {
        try {
            serverPort = (int) getAsJavaObjectFromJSON(int.class, "serverPort/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return serverPort;
    }

    public static int getTimerPeriod() {
        try {
            timerPeriod = (int) getAsJavaObjectFromJSON(int.class, "heartbeatPeriod/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return timerPeriod;
    }

    public static String getDBPath() {
        try {
            pathServerDB = (String) getAsJavaObjectFromJSON(String.class, "pathMainServerDB/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return pathServerDB;
    }

    public static int getMaxUsersInWaitingRoom() {
        try {
            maxUsersInWaitingRoom = (int) getAsJavaObjectFromJSON(int.class, "maxUsersInWaitingRoom/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }return maxUsersInWaitingRoom;
    }

    public static long getMaxTimeForACKResponseMSec() {
        try {
            maxTimeForACKResponseMSec = (long) getAsJavaObjectFromJSON(long.class, "maxTimeForACKResponseMSec/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return maxTimeForACKResponseMSec;
    }

    public static String getServerIP() {
        try {
            defaultServerIP = (String) getAsJavaObjectFromJSON(String.class, "defaultServerIP/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return defaultServerIP;
    }

    public static String getDefaultUICmd() {
        try {
            defaultUICmd = (String) getAsJavaObjectFromJSON(String.class, "defaultUICmd", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return defaultUICmd;
    }

    public static int getMaxNumOfPlayers() {
        try {
            maxNumOfPlayers = (int) getAsJavaObjectFromJSON(int.class, "maxNumOfPlayers/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        return maxNumOfPlayers;
    }
}
