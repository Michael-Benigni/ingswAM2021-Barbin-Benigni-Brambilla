package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.User;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import it.polimi.ingsw.server.utils.network.Message;
import it.polimi.ingsw.server.utils.network.ServerNetworkLayer;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class VirtualView {
    private ServerNetworkLayer network;
    private HashMap<ClientToken, User> tokenUserHashMap;
    private Controller controller;

    public VirtualView(ServerNetworkLayer network) {
        this.network = network;
        this.tokenUserHashMap = new HashMap<> ();
    }

    public synchronized void passToController(Message message, ClientToken token) throws FileNotFoundException, InvalidUserException, IllegalNumberOfPlayersException, TooManyPlayersException, ImpossibleChangingSizeException {
        this.controller.handleCommandOf(this.tokenUserHashMap.get (token), message);
    }

    public void attachController(Controller controller) {
        this.controller = controller;
    }

    public void newUser(ClientToken token) {
        this.tokenUserHashMap.put (token, new User ());
    }
}
