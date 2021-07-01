package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.utils.network.*;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.utils.Observer;

import java.util.Objects;

public class VirtualView extends AbstractView implements Observer {
    private final User user;
    private final Controller controller;
    private Integer nextProgressiveNum;

    public VirtualView(Channel channel, Controller controller) {
        super(channel);
        this.controller = controller;
        this.user = new User (this);
        this.nextProgressiveNum = 0;
    }

    public void passToController(Receivable<Command> message) throws Exception {
        this.controller.handleCommandOf(this.user, message.getInfo ());
    }

    @Override
    public void onChanged(Sendable message) {
        Sendable newMsg = MessageWriter.addPropertyTo(message, "progressiveNumber", nextProgressiveNum);
        this.getChannel ().send(newMsg);
        nextProgressiveNum++;
        if (nextProgressiveNum > ToClientMessage.maxValueProgrNum ())
            nextProgressiveNum = 0;
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
        try {
            controller.disconnect (user);
        } catch (InvalidUserException e) {
            System.out.println ("The user was still not in a waiting room and it has been disconnected.");
        }
    }
}
