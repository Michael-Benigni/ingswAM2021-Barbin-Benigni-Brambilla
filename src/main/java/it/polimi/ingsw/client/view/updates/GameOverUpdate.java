package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

import java.util.ArrayList;

public class GameOverUpdate implements ViewUpdate {
    private final ArrayList<String> winnersNames;
    private final ArrayList<String> losersNames;
    private final ArrayList<Integer> winnersVPs;
    private final ArrayList<Integer> losersVPs;
    private final String info;

    public GameOverUpdate(ArrayList<String> winnersNames, ArrayList<String> losersNames, ArrayList<Integer> winnersVPs, ArrayList<Integer> losersVPs, String info) {
        this.winnersNames = winnersNames;
        this.losersNames = losersNames;
        this.winnersVPs = winnersVPs;
        this.losersVPs = losersVPs;
        this.info = info;
    }

    @Override
    public void update(Controller controller) {
        controller.setNextState ();
        String message = "";
        String winners = String.join (", ", winnersNames) + ".";
        String losers = String.join (", ", losersNames) + ".";
        if (this.winnersNames.size () > 1)
            message += "The winners are " + winners + "\n";
        else
            message += "The winner is " + winners + "\n";
        if (this.winnersNames.size () > 1)
            message += "The losers are " + losers + "\n\n";
        else
            message += "The loser is " + losers + "\n\n";
        message += "--------------------VPs--------------------\n\n";
        message += winnersNames.stream ().map ((name) -> name + " -> VP: " + winnersVPs.get(winnersNames.indexOf (name)));
        message += "\n";
        message += losersNames.stream ().map ((name) -> name + " -> VP: " + losersVPs.get(losersNames.indexOf (name)));
        controller.getUI ().notifyMessage (message);
        if (info != null)
            controller.getUI ().notifyMessage (info);
    }
}
