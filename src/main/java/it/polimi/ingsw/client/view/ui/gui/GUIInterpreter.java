package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.ui.cli.Interpreter;

public class GUIInterpreter extends Interpreter {
    private String interaction = "";

    @Override
    public String listen() {
        return interaction;
    }

    public void addInteraction(String interaction) {
        if (!this.interaction.equals (""))
            this.interaction += " ";
        this.interaction += interaction;
    }
}
