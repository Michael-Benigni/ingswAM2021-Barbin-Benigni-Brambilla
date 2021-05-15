package it.polimi.ingsw.utils.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

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
    private final int token;
    private ChannelStatus status;

    public Channel(Socket socket, int token) {
        this.status = ChannelStatus.UNKNOWN;
        this.socket = socket;
        this.token = token;
        open();
    }

    private void open() {
        openInputScanner();
        openOutputWriter ();
        System.out.printf("Channel %d opened\n", token);
    }

    private void openInputScanner() {
        try {
            this.inSocket = new Scanner(this.socket.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            setStatus(ChannelStatus.ERROR);
            return;
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

    public void listeningLoop(ReceivableHandler receivableHandler) {
        while (true) {
            try {
                String msg = inSocket.nextLine ();
                if (QuitMessage.isQuitMessage (msg)) {
                    System.out.printf ("Client %d has closed the channel", token);
                    break;
                } else if (this.expectedACK != null && this.expectedACK.equals (new ACK (msg)))
                    this.setStatus (ChannelStatus.OPENED);
                else {
                    try {
                        Message message = new Message (msg);
                        receivableHandler.onReceived (message);
                    } catch (Exception e) {
                        send (new ErrorMessage (e.getMessage ()));
                    }
                }
            } catch (NoSuchElementException e) {
                //e.printStackTrace ();
                break;
            }
        }
        close();
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
        inSocket.close();
        outSocket.close();
        setStatus(ChannelStatus.CLOSED);
        try {
            socket.close();
            System.out.printf("Channel %d closed\n", this.token);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            setStatus(ChannelStatus.ERROR);
            return;
        }
    }

    public synchronized void send(JsonTrasmittable jsonTrasmittable) {
        outSocket.printf ("%s\n", jsonTrasmittable.transmit ());
        outSocket.flush ();
    }

    synchronized void setExpectedACK(ACK ack) {
        this.expectedACK = ack;
    }
}
