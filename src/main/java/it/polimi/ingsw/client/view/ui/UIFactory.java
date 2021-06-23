package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.ui.cli.CLI;
import it.polimi.ingsw.client.view.ui.gui.GUI;

public class UIFactory {
    public static UI getUI(String cliOrGui) {
        UI ui;
        switch (cliOrGui) {
            case "--cli": {
                ui = CLI.getInstance();
                break;
            }
            case "--gui":
            default: {
                ui = GUI.getInstance ();
                break;
            }
        }
        return ui;
    }
}
