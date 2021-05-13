package it.polimi.ingsw.server.utils.network;

import it.polimi.ingsw.server.utils.network.exception.IllegalMessageException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Channel {
    private enum ChannelStatus {
        ERROR(),
        CLOSED(),
        OPENED();
    }
    private Scanner in;
    private PrintWriter out;
    private final Socket socket;
    private final int token;
    private ChannelStatus status;

    public Channel(Socket socket, int token) {
        this.status = ChannelStatus.CLOSED;
        this.socket = socket;
        this.token = token;
        open();
    }

    private void open() {
        openInputScanner();
        openOutput();
        System.out.printf("Channel %d opened\n", token);
    }

    private void openInputScanner() {
        try {
            this.in = new Scanner(this.socket.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            setStatus(ChannelStatus.ERROR);
            return;
        }
        setStatus(ChannelStatus.OPENED);
    }

    private void openOutput() {
        try {
            this.out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            this.in.close();
            setStatus(ChannelStatus.ERROR);
            return;
        }
        setStatus(ChannelStatus.OPENED);
    }

    public void listeningLoop(ReceivableHandler receivableHandler) {
        while (true && isActive()) {
            String msg = in.nextLine();
            System.out.printf (msg + "\n");
            if (msg.equals("q"))
                break;
            else {
                try {
                    Message message = new Message (msg);
                    receivableHandler.onReceived (message);
                } catch (IllegalMessageException e) {
                    send (new ErrorMessage (e.getMessage ()));
                }
            }
        }
        close();
    }

    private void setStatus(ChannelStatus newStatus) {
        this.status = newStatus;
    }

    boolean isActive() {
        if (this.status == ChannelStatus.OPENED)
            //Possibility : send(ACK), if returns OK then...
            return true;
        return false;
    }

    void close() {
        // closing streams and socket
        in.close();
        out.close();
        try {
            socket.close();
            System.out.printf("Channel %d closed\n", this.token);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            setStatus(ChannelStatus.ERROR);
            return;
        }
        setStatus(ChannelStatus.CLOSED);
    }

    public void send(JsonTrasmittable jsonTrasmittable) {
        out.printf ("%s\n", jsonTrasmittable.transmit ());
        out.flush ();
    }
}
