package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;

public class UIFactory {
    public static UI getView(String cliOrGui) {
        switch (cliOrGui) {
            case "--cli":
                return new CLI ();
            case "--gui":
            default:
                return new GUI ();
        }
    }
}
