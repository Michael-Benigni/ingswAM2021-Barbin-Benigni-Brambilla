package it.polimi.ingsw.client.view;

import it.polimi.ingsw.utils.network.Channel;

import java.io.IOException;
import java.net.Socket;

public class ClientNetworkLayer {
    private String ip;
    private int port;

    public ClientNetworkLayer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect(View view) {
        Socket socket = null;
        try {
            socket = new Socket (ip, port);
            System.out.println("Connection established");
        } catch (IOException e) {
            System.err.println (e.getMessage ());
        }
        Channel channel = new Channel(socket, "");
        view.setChannel(channel);
        channel.listeningLoop ((msg)-> {
            ToClientMessage message = new ToClientMessage (msg);
            view.handle (message);
            System.out.printf("Received from Server %s: %s\n", msg);
        });
    }
}
