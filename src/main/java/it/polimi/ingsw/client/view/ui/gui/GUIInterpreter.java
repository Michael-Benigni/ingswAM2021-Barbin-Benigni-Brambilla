package it.polimi.ingsw.client.view.ui.gui;

import it.polimi.ingsw.client.view.ui.Interpreter;

import java.util.ArrayList;
import java.util.HashMap;

public class GUIInterpreter implements Interpreter {
    private HashMap<String, ArrayList<String>> interactions;

    public GUIInterpreter() {
        this.interactions = new HashMap<> ();
    }

    @Override
    public String listen(String nameProperty) {
        String interaction = interactions.get (nameProperty).get (0);
        interactions.get (nameProperty).remove (0);
        return interaction;
    }

    public void addInteraction(String name, String interaction) {
        if (interactions.containsKey (name))
            this.interactions.get (name).add (interaction);
        else {
            ArrayList<String> interactionsArray = new ArrayList<> ();
            interactionsArray.add (interaction);
            this.interactions.put (name, interactionsArray);
        }
    }

    public void clear() {
        interactions.clear ();
    }

    public Integer getNumInteractionsIn (String name) {
        if (interactions.containsKey (name))
            return interactions.get (name).size ();
        else
            return 0;
    }
}
