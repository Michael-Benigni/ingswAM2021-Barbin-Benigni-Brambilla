package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.config.ServerPrefs;
import it.polimi.ingsw.server.Server;

import java.util.ArrayList;
import java.util.Arrays;

public class Launcher {
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "--gui":
                case "--cli": {
                    Client.launch (getPort (args), args);
                    break;
                }
                default:
                    Server.launch (getPort (args));
            }
        }
        else
            Server.launch (8888);
    }


    /**
     * This method parse the command line arguments to get the port, otherwise the port is loaded by default from a
     * configuration file
     * @param args command line arguments
     * @return the port of the server
     */
    private static int getPort(String[] args) {
        ArrayList<String> cmds = new ArrayList<>(Arrays.asList(args));
        if (cmds.contains("-p")) {
            int indexPort = cmds.indexOf("-p") + 1;
            return Integer.parseInt(args[indexPort]);
        }
        return ServerPrefs.getServerPort();
    }
}
