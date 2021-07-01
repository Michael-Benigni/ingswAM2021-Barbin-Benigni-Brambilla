package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.ClientPrefs;
import it.polimi.ingsw.client.view.lightweightmodel.LWModel;
import it.polimi.ingsw.client.view.ui.UI;
import it.polimi.ingsw.utils.network.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;

public class Controller {
    private Channel channel;
    private final UI ui;
    private final LWModel model;
    private ArrayDeque<ToClientMessage> messagesDeque;
    private Integer expectedMsgProgNum;

    public Controller(UI ui) {
        this.ui = ui;
        this.model = new LWModel ();
        this.model.attach (ui);
        this.messagesDeque = new ArrayDeque<>();
        this.expectedMsgProgNum = 0;
    }

    public void loop() {
        new Thread (()->
        {
            Sendable sendable;
            do {
                sendable = getNextMove ();
                this.channel.send (sendable);
            } while (!QuitMessage.isQuitMessage (sendable.transmit ()));

        }).start ();
    }

    public synchronized void setNextState() {
        this.ui.setNextState ();
    }

    private Sendable getNextMove() {
        return this.getUI ().getNextMessage ();
    }

    public synchronized void setChannel(Channel channel) {
        this.channel = channel;
    }

    public synchronized void handle(ToClientMessage message) {
        this.messagesDeque.addLast (message);
        reorderDeque();
        ToClientMessage nextToHandle = this.messagesDeque.getFirst ();
        while (!nextToHandle.getProgressiveNumber ().equals(this.expectedMsgProgNum)) {
            try {
                wait ();
                nextToHandle = this.messagesDeque.getFirst ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
        nextToHandle.getInfo ().update (this);
        this.messagesDeque.removeFirst ();
        expectedMsgProgNum++;
        if (expectedMsgProgNum > ToClientMessage.maxValueProgrNum ())
            expectedMsgProgNum = 0;
        notifyAll ();

    }

    private void reorderDeque() {
        ArrayList<ToClientMessage> toClientMessages = new ArrayList<> (this.messagesDeque);
        toClientMessages.sort (Comparator.comparingInt (ToClientMessage::getProgressiveNumber));
        this.messagesDeque = new ArrayDeque<> (toClientMessages);
    }

    public synchronized void handleError(ErrorMessage errorMessage) {
        ui.notifyError(errorMessage.getInfo ());
    }

    public synchronized UI getUI() {
        return this.ui;
    }

    public synchronized LWModel getModel() {
        return model;
    }
}
