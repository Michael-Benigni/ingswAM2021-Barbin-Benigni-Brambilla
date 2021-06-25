package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class UserInfoUP implements ViewUpdate {
    private final String name;

    public UserInfoUP(String name) {
        this.name = name;
    }

    @Override
    public void update(Controller controller) {
        controller.getModel ().getInfoMatch ().putNewUser(name);
    }
}