package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.ui.cli.CLI;
import it.polimi.ingsw.client.view.ui.gui.GUI;

public class UIFactory {
    public static UI getUI(String cliOrGui) {
        UI ui;
        switch (cliOrGui) {
            case "--cli": {
                CLI cli = new CLI ();
                cli.start ();
                ui = cli;
                break;
            }
            case "--gui":
            default: {
                ui = new GUI ();
                break;
            }
        }
        return ui;
    }
}
