package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.Controller;
import it.polimi.ingsw.utils.network.exception.ConnectionRefusedException;
import it.polimi.ingsw.utils.network.exception.IllegalMessageException;

import java.io.IOException;
import java.net.Socket;

public class ClientNetworkLayer {
    private final String ip;
    private final int port;

    public ClientNetworkLayer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect(Controller controller) throws ConnectionRefusedException {
        Socket socket = getSocket ();
        controller.getUI ().connectionSuccessful();
        Channel channel = new Channel(socket, "");
        controller.setChannel(channel);
        controller.loop ();
        channel.listeningLoop ((msg)-> {
            if (QuitMessage.isQuitMessage (msg))
                channel.close ();
            else if (ACK.isACKMessage (msg))
                responseACK (channel, msg);
            else if(ErrorMessage.isErrorMessage(msg))
                controller.handleError(new ErrorMessage (msg));
            else {
                System.out.println (" DEBUG: " + msg);
                ToClientMessage message = new ToClientMessage (msg);
                controller.handle (message);
            }
        });
        System.out.println ("CLIENT CLOSED.\n");
    }

    private Socket getSocket() throws ConnectionRefusedException {
        Socket socket = null;
        try {
            socket = new Socket (ip, port);
            System.out.println("Connection established");
        } catch (IOException e) {
            System.err.println (e.getMessage ());
        }
        if (socket == null)
            throw new ConnectionRefusedException ();
        return socket;
    }

    private void responseACK(Channel channel, String msg) {
        try {
            channel.send (new ACK (msg));
        } catch (IllegalMessageException e) {
            e.printStackTrace ();
        }
    }
}
