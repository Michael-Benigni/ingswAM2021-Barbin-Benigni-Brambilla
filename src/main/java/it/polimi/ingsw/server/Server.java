package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.utils.config.Prefs;
import it.polimi.ingsw.utils.network.ServerNetworkLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * It's the Server of the Masters of Renaissance game
 */
public class Server
{
    /**
     * This method launches the Server, builds the Controller and starts the ClientNetworkLayer loop
     * @param port port where the server will listen
     */
    public static void launch(int port) {
        System.out.println("Server started!");
        Controller controller = new Controller ();
        ServerNetworkLayer network = new ServerNetworkLayer (controller, port, Prefs.getTimerPeriod(), Prefs.getMaxTimeForACKResponseMSec ());
        network.listening ();
    }
}
