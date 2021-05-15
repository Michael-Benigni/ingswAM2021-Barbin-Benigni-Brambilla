package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.utils.network.Channel;
import it.polimi.ingsw.utils.network.JsonTrasmittable;
import it.polimi.ingsw.utils.network.Message;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientNetworkLayer {
    private String ip;
    private int port;

    public ClientNetworkLayer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) {
        ClientNetworkLayer client = new ClientNetworkLayer ("127.0.0.1", 2468);
        try {
            client.startClient ();
        } catch (IOException e) {
            System.err.println (e.getMessage ());
        }
    }

    public void startClient() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        Channel channel = new Channel(socket, 1);
        new Thread (()-> {
            int i = 0;
            if (i == 0) {
                Scanner scanner = new Scanner (System.in);
                String mess = scanner.nextLine ();
                i++;
                try {
                    channel.send (new Message(mess));
                } catch (IllegalMessageException e) {
                    e.printStackTrace ();
                }
            }
        }).start ();
        channel.listeningLoop((msg)-> {
        });
    }
}
