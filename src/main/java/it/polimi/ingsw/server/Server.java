package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.utils.config.Prefs;
import it.polimi.ingsw.server.utils.network.ServerNetworkLayer;
import it.polimi.ingsw.server.view.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * It's the Server of the Masters of Renaissance game
 */
public class Server
{
    /**
     * This method launches the Server, builds the Controller and starts the ClientNetworkLayer loop
     * @param args line command parameters
     */
    public static void launch( String[] args ) {
        System.out.println("Server started!");
        ServerNetworkLayer network = new ServerNetworkLayer (getPort(args), Prefs.getTimerPeriod());
        Controller controller = new Controller ();
        VirtualView virtualView = new VirtualView (network);
        virtualView.attachController (controller);
        network.listening (virtualView);
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
        return Prefs.getServerPort();
    }

}
