package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.server.view.AbstractView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Channel {
    private final boolean isClientSideChannel;

    enum ChannelStatus {
        ERROR(),
        CLOSED(),
        OPENED(),
        UNKNOWN();

    }
    private AbstractView view;
    private ACK expectedACK;
    private Scanner inSocket;
    private PrintWriter outSocket;
    private final Socket socket;
    private final String id;
    private ChannelStatus status;


    public Channel(Socket socket, String id) {
        this.status = ChannelStatus.UNKNOWN;
        this.socket = socket;
        this.id = id;
        this.isClientSideChannel = this.id.equals ("");
        this.open();
    }

    private void open() {
        openInputReader ();
        openOutputWriter ();
        System.out.printf("Channel %s opened\n", this.id);
    }

    private void openInputReader() {
        try {
            this.inSocket = new Scanner (new InputStreamReader (this.socket.getInputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            inSocket.close ();
            setStatus(ChannelStatus.ERROR);
            return;
        } catch (NullPointerException e) {
            System.err.println ("Socket not open on port %d");
        }
        setStatus(ChannelStatus.UNKNOWN);
    }

    private void openOutputWriter() {
        try {
            this.outSocket = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            setStatus(ChannelStatus.ERROR);
            this.inSocket.close ();
            return;
        }
        setStatus(ChannelStatus.UNKNOWN);
    }

    public void listeningLoop(Receiver receiver) {
        String msg;
        while (!status.equals (ChannelStatus.CLOSED) && !status.equals (ChannelStatus.ERROR) && inSocket.hasNextLine ()) {
            msg = inSocket.nextLine ();
            if (msg != null) {
                String finalMsg = msg;
                new Thread (() -> {
                    try {
                        receiver.onReceived (finalMsg);
                    } catch (Exception e) {
                        if (!isClientSideChannel) {
                            System.out.printf ("NETWORK ERROR: Invalid message \"" + finalMsg + "\" from Client n°%s!\n", this.id);
                            send (new ErrorMessage (e));
                        } else {
                            System.out.println ("NETWORK ERROR: Invalid message \"" + finalMsg + "\" from Server!");
                            System.out.println ("DESCRIPTION: " + e.getMessage ());
                        }
                    }
                }).start ();
            }
        }
    }

    synchronized void setStatus(ChannelStatus newStatus) {
        this.status = newStatus;
        notifyAll ();
    }

    synchronized boolean isActive() {
        if (this.status == ChannelStatus.OPENED) {
            this.status = ChannelStatus.UNKNOWN;
            return true;
        }
        return false;
    }

    void close() {
        // closing streams and socket
        if (!isClientSideChannel)
            send (new QuitMessage ());
        else
            System.out.println ("\n\n\n----------------------------------------------------");
        try {
            socket.close();
            System.out.printf("Channel %s closed\n", this.id);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            setStatus(ChannelStatus.ERROR);
            return;
        }
        inSocket.close();
        outSocket.close();
        setStatus(ChannelStatus.CLOSED);
        if (!isClientSideChannel)
            view.disconnect();
    }

    public synchronized void send(Sendable sendable) {
        outSocket.printf ("%s\n", sendable.transmit ());
        outSocket.flush ();
        if (!isClientSideChannel) {
            System.out.printf ("Sent to Client n°%s: %s\n", id, sendable.transmit ());
        }
    }

    synchronized void setExpectedACK(ACK ack) {
        this.expectedACK = ack;
    }

    public void setView(AbstractView view) {
        this.view = view;
    }

    ACK getExpectedACK() {
        return this.expectedACK;
    }

    ChannelStatus getStatus() {
        return this.status;
    }
}
