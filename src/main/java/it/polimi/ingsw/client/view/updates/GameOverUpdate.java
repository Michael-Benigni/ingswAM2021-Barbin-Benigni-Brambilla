package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

import java.util.ArrayList;

public class GameOverUpdate implements ViewUpdate {
    private final ArrayList<Integer> winnersRoundPositions;
    private final ArrayList<Integer> losersRoundPositions;
    private final ArrayList<Integer> winnersVPs;
    private final ArrayList<Integer> losersVPs;

    public GameOverUpdate(ArrayList<Integer> winnersRoundPositions, ArrayList<Integer> losersRoundPositions, ArrayList<Integer> winnersVPs, ArrayList<Integer> losersVPs) {
        this.winnersRoundPositions = winnersRoundPositions;
        this.losersRoundPositions = losersRoundPositions;
        this.winnersVPs = winnersVPs;
        this.losersVPs = losersVPs;
    }

    @Override
    public void update(Controller controller) {
        controller.setNextState ();
        String message = "";
        ArrayList<String> losersNames = getPlayersNames (controller, losersRoundPositions);
        ArrayList<String> winnersNames = getPlayersNames (controller, winnersRoundPositions);
        String winners = String.join (", ", winnersNames) + ".";
        String losers = String.join (", ", losersNames) + ".";
        if (winnersRoundPositions.size () > 1)
            message += "The winners are " + winners + "\n";
        else
            message += "The winner is " + winners + "\n";
        if (winnersRoundPositions.size () > 1)
            message += "The losers are " + losers + "\n\n";
        else
            message += "The loser is " + losers + "\n\n";
        message += "--------------------VPs--------------------\n\n";
        message += winnersNames.stream ().map ((name) -> name + " -> VP: " + winnersVPs.get(winnersNames.indexOf (name)));
        message += "\n";
        message += losersNames.stream ().map ((name) -> name + " -> VP: " + losersVPs.get(losersNames.indexOf (name)));
        controller.getUI ().notifyMessage (message);
    }

    private ArrayList<String> getPlayersNames(Controller controller, ArrayList<Integer> positions) {
        ArrayList<String> names = new ArrayList<> ();
        for (Integer position : positions)
            names.add (controller.getModel ().getInfoMatch ().getPlayerAt (position));
        return  names;
    }
}
