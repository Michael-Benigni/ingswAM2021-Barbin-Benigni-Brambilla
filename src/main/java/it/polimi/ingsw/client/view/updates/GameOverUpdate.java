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
        controller.getUI ().onGameOver (winnersNames, losersNames, winnersVPs, losersVPs, info);
    }
}
