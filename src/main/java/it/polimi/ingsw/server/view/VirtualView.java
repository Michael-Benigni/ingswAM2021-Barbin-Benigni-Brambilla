package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.network.Channel;
import it.polimi.ingsw.utils.network.Receivable;

import java.util.Objects;

public class VirtualView implements Observer {
    private final Channel channel;
    private final User user;
    private final Controller controller;

    public VirtualView(Channel channel, Controller controller) {
        this.controller = controller;
        this.channel = channel;
        this.user = new User (this);
    }

    public void passToController(Receivable<Command> message) throws Exception {
        this.controller.handleCommandOf(this.user, message.getInfo ());
    }


    private void propagate(Update update) {
        this.channel.send(update);
    }

    @Override
    public void update(Update update) {
        propagate (update);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualView)) return false;
        VirtualView that = (VirtualView) o;
        return channel.equals (that.channel) && user.equals (that.user) && controller.equals (that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash (channel, user, controller);
    }
}
