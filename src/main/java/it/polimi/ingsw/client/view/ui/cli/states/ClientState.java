package it.polimi.ingsw.client.view.ui.cli.states;

import it.polimi.ingsw.client.view.moves.PlayMove;

import java.util.HashMap;

public abstract class ClientState {
    private static HashMap<String, String> nameAndDesc;

    protected ClientState() {
        nameAndDesc = new HashMap<> ();
        addAvailableMove (PlayMove.QUIT.getCmd (), "QUIT THE GAME");
    }

    public static String menu() {
        String menu = "You can perform these actions: ";
        for (String key : nameAndDesc.keySet ())
            menu = menu + "\n" + key + (!key.equals ("") ? " -> " : "") + nameAndDesc.get (key) ;
        return menu;
    }

    public abstract ClientState getNextState();

    protected static void addAvailableMove(String name, String desc) {
        nameAndDesc.put (name, desc);
    }
}
