package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;

public class ViewFactory {
    public static View getView(String cliOrGui) {
        switch (cliOrGui) {
            case "--cli":
                return new CLI ();
            case "--gui":
            default:
                return new GUI ();
        }
    }
}
