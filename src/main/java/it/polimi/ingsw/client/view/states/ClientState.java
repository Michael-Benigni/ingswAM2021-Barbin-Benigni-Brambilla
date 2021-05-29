package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.moves.Move;

import java.util.HashMap;

public abstract class ClientState {
    private static HashMap<String, String> nameAndDesc;

    protected ClientState() {
        nameAndDesc = new HashMap<> ();
    }

    private Move menuMove() {
        return (interpreter, interlocutor) -> {
            interlocutor.write (menu ());
            return null;
        };
    }

    public static String menu() {
        String menu = "You can perform these actions: \n";
        for (String key : nameAndDesc.keySet ())
            menu = menu + key + " -> " + nameAndDesc.get (key) + "\n";
        return menu;
    }

    public abstract ClientState getNextState();

    protected static void addAvailableMove(String name, String desc) {
        nameAndDesc.put (name, desc);
    }
}
