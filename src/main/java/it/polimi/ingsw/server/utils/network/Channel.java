package it.polimi.ingsw.server.utils.network;

import it.polimi.ingsw.client.view.MessageHandler;
import it.polimi.ingsw.server.utils.config.JsonHandler;

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

    public void listeningLoop(MessageHandler messageHandler) {
        while (true && isActive()) {
            String msg = in.nextLine();
            if (msg.equals("q"))
                break;
            else {
                messageHandler.onLoop(deserialize (msg));
            }
        }
        close();
    }

    private Message deserialize(String message) {
        return (Message) JsonHandler.fromJson(message, Message.class);
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
        // chiudo gli stream e il socket
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

    //TODO: Trasmittable ?
    public void send(Message message) {
        out.printf("%s\n", message/*.transmit ()*/);
        out.flush();
    }
}
