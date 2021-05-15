package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.utils.config.Prefs;
import it.polimi.ingsw.server.Server;

import java.io.FileNotFoundException;

public class Launcher {
    public static void main(String[] args) {
        try {
            Prefs.load();
        } catch (FileNotFoundException e) {
            System.err.printf("Unable to load preferences");
        }
        switch (args[0]) {
            case "-server":
                Server.launch(args);
            case "-client":
                Client.launch(args);
        }
    }
}
