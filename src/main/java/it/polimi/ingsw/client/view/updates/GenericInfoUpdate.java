package it.polimi.ingsw.client.view.updates;

import it.polimi.ingsw.client.view.Controller;

public class GenericInfoUpdate implements ViewUpdate {
    private final String text;

    public GenericInfoUpdate(String text) {
        this.text = text;
    }

    @Override
    public void update(Controller controller) {
        controller.getUI ().notifyMessage (text);
    }
}
