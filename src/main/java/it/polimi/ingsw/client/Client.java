package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.ClientNetworkLayer;
import it.polimi.ingsw.client.view.ui.UIFactory;
import it.polimi.ingsw.utils.config.ServerPrefs;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    public static void launch(int port, String[] args) {
        ClientNetworkLayer network = new ClientNetworkLayer (getIP (args), port);
        UI ui = UIFactory.getUI (getCLIorGUI (args));
        Controller controller = new Controller (ui);
        ui.setController (controller);
        ui.start ();
        network.connect (controller);
    }

    private static String getIP(String[] args) {
        ArrayList<String> cmds = new ArrayList<>(Arrays.asList(args));
        if (cmds.contains("-s")) {
            int indexIP = cmds.indexOf("-s") + 1;
            return args[indexIP];
        }
        return ServerPrefs.getServerIP();
    }

    private static String getCLIorGUI(String[] args) {
        ArrayList<String> cmds = new ArrayList<>(Arrays.asList(args));
        String CLI = "--cli";
        String GUI = "--gui";
        return  cmds.contains (CLI) ? CLI
                : cmds.contains (GUI) ? GUI
                : ServerPrefs.getDefaultUICmd();
    }
}
