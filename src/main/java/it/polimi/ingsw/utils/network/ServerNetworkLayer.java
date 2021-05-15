package it.polimi.ingsw.utils.network;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.view.ClientToken;
import it.polimi.ingsw.server.view.VirtualView;
import it.polimi.ingsw.utils.Entry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private final Controller controller;
    private ExecutorService executor;
    private ServerSocket serverSocket;
    private Timer timer;
    private final int TIMER_PERIOD_HEARTBEAT;
    private final long MAX_TIME_FOR_ACK_RESP;
    private ArrayList<Entry<ClientToken, Channel>> channels;


    /**
     * Constructor.
     * @param controller
     * @param port The port on which all the connections are accepted
     * @param timerPeriodHeartbeat
     * @param maxTimeForACKResp
     */
    public ServerNetworkLayer(Controller controller, int port, int timerPeriodHeartbeat, long maxTimeForACKResp) {
        this.port = port;
        this.controller = controller;
        this.TIMER_PERIOD_HEARTBEAT = timerPeriodHeartbeat;
        this.MAX_TIME_FOR_ACK_RESP = maxTimeForACKResp;
        this.executor = Executors.newCachedThreadPool();
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println (e.getMessage());
            return;
        }
        this.channels = new ArrayList<> ();
        this.timer = new Timer();
    }


    /**
     * It's the main loop: the ClientNetworkLayer is in an infinite loop during which it is listening for new connection requests
     * from other clients, and for each client request is run a new thread that handles the client's the request.
     * The threads are taken from the ExecutorService, and so they are generated only if it is necessary.
     */
    public void listening() {
        System.out.printf("Server ready on port: %d\n" + "Accepting...\n", this.port);
        heartbeat();
        while (true) {
            try {
                Socket socket = this.serverSocket.accept();
                System.out.printf("Client %d accepted!\n", this.channels.size () + 1);
                executor.submit(()->handleConnectionRequest(socket, this.channels.size () + 1));
            } catch(IOException e) {
                System.err.println (e.getMessage ());
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
    private void handleConnectionRequest(Socket socket, int clientToken) {
        Channel channel = new Channel(socket, clientToken);
        ClientToken token = new ClientToken ();
        this.channels.add (new Entry<> (token, channel));
        VirtualView view = new VirtualView (channel, this.controller);
        channel.listeningLoop((msg) -> {
            System.out.printf("Received from Client %s: %s\n", token, msg);
            view.passToController (msg);
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
    private int detectDisconnections() {
        for (int indexCh = 0; indexCh < channels.size (); indexCh++) {
            Entry<ClientToken, Channel> entry = channels.get (indexCh);
            Channel channel = entry.getValue ();
            ACK ack = new ACK ();
            channel.send (ack);
            channel.setExpectedACK (ack);
            if (!channel.isActive ()) {
                synchronized (channel) {
                    try {
                        channel.wait (MAX_TIME_FOR_ACK_RESP);
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
                if (!channel.isActive ()) {
                    System.out.printf ("Client %d disconnected!\n", this.channels.indexOf (entry) + 1);
                    this.channels.remove (entry);
                    channel.close ();
                }
            }
        } return this.channels.size();
    }
}
