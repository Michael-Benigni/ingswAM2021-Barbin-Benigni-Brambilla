package it.polimi.ingsw.utils.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Channel {
    enum ChannelStatus {
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
        while (inSocket.hasNextLine ()) {
            msg = inSocket.nextLine ();
            if (msg != null) {
                try {
                    receiver.onReceived (msg);
                } catch (Exception e) {
                    e.printStackTrace ();
                    send (new ErrorMessage (e));
                }
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

    ACK getExpectedACK() {
        return this.expectedACK;
    }

    ChannelStatus getStatus() {
        return this.status;
    }
}
