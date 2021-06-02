package it.polimi.ingsw.client.view.ui;

import it.polimi.ingsw.client.view.ui.cli.CLI;
import it.polimi.ingsw.client.view.ui.gui.GUI;

public class UIFactory {
    public static UI getUI(String cliOrGui) {
        switch (cliOrGui) {
            case "--cli":
                return new CLI ();
            case "--gui":
            default:
                return new GUI ();
        }
    }
}
