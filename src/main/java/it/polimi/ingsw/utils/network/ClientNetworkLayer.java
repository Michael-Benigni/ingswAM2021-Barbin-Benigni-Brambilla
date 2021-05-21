package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.client.view.View;
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
        view.loop ();
        channel.listeningLoop ((msg)-> {
            System.out.print (msg + "\n");
            if(ACK.isACKMessage (msg))
                channel.send (new ACK (msg));
            else if(ValidMoveMessage.isValidMoveMessage(msg))
                view.readyForNextMove ();
            else {
                System.out.printf ("Received from Server: %s\n", msg);
                ToClientMessage message = new ToClientMessage (msg);
                view.handle (message);
            }
        });
    }
}
