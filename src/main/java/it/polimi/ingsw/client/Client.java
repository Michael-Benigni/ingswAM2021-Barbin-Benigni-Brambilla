package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ClientNetworkLayer;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.UIFactory;
import it.polimi.ingsw.utils.config.Prefs;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    public static void launch(int port, String[] args) {
        ClientNetworkLayer network = new ClientNetworkLayer (getIP (args), port);
        View view = new View (UIFactory.getView(getCLIorGUI (args)));
        network.connect (view);
    }

    private static String getIP(String[] args) {
        ArrayList<String> cmds = new ArrayList<>(Arrays.asList(args));
        if (cmds.contains("-s")) {
            int indexIP = cmds.indexOf("-s") + 1;
            return args[indexIP];
        }
        return Prefs.getServerIP();
    }

    private static String getCLIorGUI(String[] args) {
        ArrayList<String> cmds = new ArrayList<>(Arrays.asList(args));
        String CLI = "--cli";
        String GUI = "--gui";
        return  cmds.contains (CLI) ? CLI
                : cmds.contains (GUI) ? GUI
                : Prefs.getDefaultUICmd();
    }
}
