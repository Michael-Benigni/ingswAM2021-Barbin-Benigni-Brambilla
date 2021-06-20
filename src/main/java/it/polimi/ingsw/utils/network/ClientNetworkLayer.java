package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

import java.io.IOException;
import java.net.Socket;

public class ClientNetworkLayer {
    private String ip;
    private int port;

    public ClientNetworkLayer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect(Controller controller) {
        Socket socket = null;
        try {
            socket = new Socket (ip, port);
            System.out.println("Connection established");
        } catch (IOException e) {
            System.err.println (e.getMessage ());
        }
        Channel channel = new Channel(socket, "");
        controller.setChannel(channel);
        controller.loop ();
        channel.listeningLoop ((msg)-> {
            // DEBUG: System.out.print (msg + "\n");
            if (QuitMessage.isQuitMessage (msg))
                channel.close ();
            else if (ACK.isACKMessage (msg)) {
                responseACK(channel, msg);
            }
            else if(ErrorMessage.isErrorMessage(msg))
                controller.handleError(new ErrorMessage (msg));
            else {
                // DEBUG: System.out.printf ("Received from Server: %s\n", msg);
                ToClientMessage message = new ToClientMessage (msg);
                controller.handle (message);
            }
        });
        System.out.println ("CLIENT CLOSED.\n");
    }

    private void responseACK(Channel channel, String msg) {
        new Thread (() -> {
            try {
                channel.send (new ACK (msg));
            } catch (IllegalMessageException e) {
                e.printStackTrace ();
            }
        }).start ();
    }
}
