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


    public static void load() {
        try {
            serverPort = (int) getAsJavaObjectFromJSON(int.class, "serverPort/", PATH_TO_PREFS);
            timerPeriod = (int) getAsJavaObjectFromJSON(int.class, "heartbeatPeriod/", PATH_TO_PREFS);
            pathServerDB = (String) getAsJavaObjectFromJSON(String.class, "pathMainServerDB/", PATH_TO_PREFS);
            maxUsersInWaitingRoom = (int) getAsJavaObjectFromJSON(int.class, "maxUsersInWaitingRoom/", PATH_TO_PREFS);
            maxTimeForACKResponseMSec = (long) getAsJavaObjectFromJSON(long.class, "maxTimeForACKResponseMSec/", PATH_TO_PREFS);
            defaultServerIP = (String) getAsJavaObjectFromJSON(String.class, "defaultServerIP/", PATH_TO_PREFS);
            defaultUICmd = (String) getAsJavaObjectFromJSON(String.class, "defaultUICmd", PATH_TO_PREFS);
            maxNumOfPlayers = (int) getAsJavaObjectFromJSON(int.class, "maxNumOfPlayers/", PATH_TO_PREFS);
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static int getTimerPeriod() {
        load ();
        return timerPeriod;
    }

    public static String getDBPath() {
        load ();
        return pathServerDB;
    }

    public static int getMaxUsersInWaitingRoom() {
        load ();
        return maxUsersInWaitingRoom;
    }

    public static long getMaxTimeForACKResponseMSec() {
        load ();
        return maxTimeForACKResponseMSec;
    }

    public static String getServerIP() {
        load ();
        return defaultServerIP;
    }

    public static String getDefaultUICmd() {
        load ();
        return defaultUICmd;
    }

    public static int getMaxNumOfPlayers() {
        load ();
        return maxNumOfPlayers;
    }
}
