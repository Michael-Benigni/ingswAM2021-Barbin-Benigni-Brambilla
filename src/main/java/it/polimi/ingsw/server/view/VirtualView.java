package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.utils.network.Sendable;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.utils.network.Channel;
import it.polimi.ingsw.utils.network.Receivable;

import java.util.Objects;

public class VirtualView extends AbstractView implements Observer {
    private final User user;
    private final Controller controller;

    public VirtualView(Channel channel, Controller controller) {
        super(channel);
        this.controller = controller;
        this.user = new User (this);
    }

    public void passToController(Receivable<Command> message) throws Exception {
        this.controller.handleCommandOf(this.user, message.getInfo ());
    }

    @Override
    public void onChanged(Sendable message) {
        this.getChannel ().send(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VirtualView)) return false;
        VirtualView that = (VirtualView) o;
        return getChannel ().equals (that.getChannel ()) && user.equals (that.user) && controller.equals (that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash (getChannel (), user, controller);
    }

    @Override
    public void disconnect() {
        controller.remove(user);
    }
}
