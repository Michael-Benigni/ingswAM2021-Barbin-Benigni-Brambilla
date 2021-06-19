package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class PlayerInfoUP implements ViewUpdate {
    private final int positionInTurn;
    private final String name;

    public PlayerInfoUP(int positionInTurn, String name) {
        this.positionInTurn = positionInTurn;
        this.name = name;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel ().getInfoMatch ().putNewPlayer(positionInTurn, name);
    }
}
