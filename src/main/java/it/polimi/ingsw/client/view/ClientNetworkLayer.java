package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.server.utils.network.Channel;
import it.polimi.ingsw.server.utils.network.JsonTrasmittable;

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
        Gson gson = new Gson ();
        new Thread (()-> {
            while (true) {
                Scanner scanner = new Scanner (System.in);
                String mess = scanner.nextLine ();
                channel.send (new JsonTrasmittable () {
                    @Override
                    public String toString() {
                        return mess;
                    }
                });
            }
        }).start ();
        channel.listeningLoop((msg)-> {
            //TODO: do something
            System.out.printf("Received: %s\n", msg);
        });
    }
}
