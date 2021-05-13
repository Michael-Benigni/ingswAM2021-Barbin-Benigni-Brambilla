package it.polimi.ingsw.server.utils.network;

import it.polimi.ingsw.server.view.ClientToken;
import it.polimi.ingsw.server.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * It's the class that manages all the connections between server and clients. It accepts new connections and detects if
 * a connection has been lost
 */
public class ServerNetworkLayer {
    private final int port;
    private ExecutorService executor;
    private ServerSocket serverSocket;
    private Timer timer;
    private final int TIMER_PERIOD_HEARTBEAT;
    private HashMap<ClientToken, Channel> channels;


    /**
     * Constructor.
     * @param port The port on which all the connections are accepted
     * @param timerPeriodHeartbeat
     */
    public ServerNetworkLayer(int port, int timerPeriodHeartbeat) {
        this.port = port;
        this.TIMER_PERIOD_HEARTBEAT = timerPeriodHeartbeat;
        this.executor = Executors.newCachedThreadPool();
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            // port not available
            System.err.println(e.getMessage());
            return;
        }
        this.channels = new HashMap<> ();
        this.timer = new Timer();
    }


    /**
     * It's the main loop: the ClientNetworkLayer is in an infinite loop during which it is listening for new connection requests
     * from other clients, and for each client request is run a new thread that handles the client's the request.
     * The threads are taken from the ExecutorService, and so they are generated only if it is necessary.
     */
    public void listening(VirtualView virtualView) {
        System.out.printf("Server ready on port: %d\n" + "Accepting...\n", this.port);
        heartbeat();
        while (true) {
            try {
                Socket socket = this.serverSocket.accept();
                System.out.printf("Client %d accepted!\n", this.channels.size () + 1);
                executor.submit(()->handleConnectionRequest(socket, this.channels.size () + 1, virtualView));
            } catch(IOException e) {
                // serverSocket di chiude
                break;
            }
        }
        this.executor.shutdown();
    }


    /**
     * this is the method that is invoked in the main loop when a client request is sent: here is crated the Channel of
     * the connection and it is started the loop to listen all the messages sent by the client connected
     * @param socket socket on which is built the Channel
     * @param clientToken number of the client connected to the Channel built in this method
     */
    private void handleConnectionRequest(Socket socket, int clientToken, VirtualView view) {
        Channel channel = new Channel(socket, clientToken);
        ClientToken token = new ClientToken ();
        this.channels.put (token, channel);
        view.newUser(token);
        channel.listeningLoop((msg) -> {
            try {
                view.passToController (msg, token);
            } catch (Exception e) {
               //TODO: channel.send ();
            }
            System.out.printf("Received from %s: %s\n", token, msg);
        });
    }


    /**
     * This method schedules a Timer: at each period is don a check on the connection of the Channels
     */
    private void heartbeat() {
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int numConnections = ServerNetworkLayer.this.detectDisconnections();
                System.out.printf("heartbeat - %d connections\n", numConnections);
            }
        }, TIMER_PERIOD_HEARTBEAT, TIMER_PERIOD_HEARTBEAT);
    }


    /**
     * This method checks if some connection has been closed, and if any removes these from the Channels List
     * @return the number of Channels opened
     */
    private synchronized int detectDisconnections() {
        for (ClientToken token : channels.keySet ()) {
            if (!channels.get(token).isActive())
                channels.remove(token);
        }
        return this.channels.size();
    }
}
