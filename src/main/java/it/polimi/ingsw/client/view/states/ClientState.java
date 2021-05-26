package it.polimi.ingsw.client.view.states;

import it.polimi.ingsw.client.view.exceptions.UnavailableMoveName;

import java.util.HashMap;

public abstract class ClientState {
    private HashMap<String, Move> availableMoves;
    private HashMap<String, String> nameAndDesc;

    protected ClientState() {
        this.availableMoves = new HashMap<> ();
        this.nameAndDesc = new HashMap<> ();
        addAvailableMove ("MENU", menuMove(), "SHOW THE MENU");
    }

    private Move menuMove() {
        return (interpreter, interlocutor) -> {
            interlocutor.write (menu ());
            return null;
        };
    }

    public String menu() {
        String menu = "\nYou can perform these actions: \n";
        for (String key : nameAndDesc.keySet ())
            menu = menu + key + " -> " + nameAndDesc.get (key) + "\n";
        return menu;
    }

    public abstract ClientState getNextState();

    protected void addAvailableMove(String name, Move move, String desc) {
        this.availableMoves.put (name, move);
        this.nameAndDesc.put (name, desc);
    }

    public Move getMove(String moveAsString) throws UnavailableMoveName {
        for (String moveName : availableMoves.keySet ()) {
            if (moveAsString.equals (moveName))
                return availableMoves.get (moveAsString);
        }
        throw new UnavailableMoveName ();
    }
}
