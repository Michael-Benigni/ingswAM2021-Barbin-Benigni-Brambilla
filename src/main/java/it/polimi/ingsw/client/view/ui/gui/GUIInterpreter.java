package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.ui.Interpreter;

import java.util.HashMap;

public class GUIInterpreter implements Interpreter {
    private HashMap<String, String> interactions;

    public GUIInterpreter() {
        this.interactions = new HashMap<> ();
    }

    @Override
    public String listen(String nameProperty) {
        return interactions.get (nameProperty);
    }

    public void addInteraction(String name, String interaction) {
        this.interactions.put (name, interaction);
    }
}
