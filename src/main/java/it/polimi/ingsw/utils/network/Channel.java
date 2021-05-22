package it.polimi.ingsw.utils.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Channel {
    private enum ChannelStatus {
        ERROR(),
        CLOSED(),
        OPENED(),
        UNKNOWN();
    }

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
        open();
    }

    private void open() {
        openInputScanner();
        openOutputWriter ();
        System.out.printf("Channel %s opened\n", this.id);
    }

    private void openInputScanner() {
        try {
            this.inSocket = new Scanner(this.socket.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
            this.inSocket.close();
            return;
        }
        setStatus(ChannelStatus.UNKNOWN);
    }

    public void listeningLoop(Receiver receiver) {
        while (true) {
            String msg = inSocket.nextLine ();
            if (QuitMessage.isQuitMessage (msg)) {
                System.out.printf ("Client %s has closed the channel", this.id);
                if (this.status != ChannelStatus.CLOSED)
                    close ();
                break;
            } else if (this.expectedACK != null && this.expectedACK.isTheSameACK (msg))
                this.setStatus (ChannelStatus.OPENED);
            else {
                try {
                    receiver.onReceived (msg);
                } catch (Exception e) {
                    send (new ErrorMessage (e.getMessage ()));
                }
            }
        }
    }

    private synchronized void setStatus(ChannelStatus newStatus) {
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
    }

    public synchronized void send(Sendable sendable) {
        outSocket.printf ("%s\n", sendable.transmit ());
        outSocket.flush ();
    }

    synchronized void setExpectedACK(ACK ack) {
        this.expectedACK = ack;
    }
}
