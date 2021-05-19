package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.exception.FirstWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.FullWaitingRoomException;
import it.polimi.ingsw.server.controller.exception.ImpossibleChangingSizeException;
import it.polimi.ingsw.server.controller.exception.InvalidUserException;
import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.GameFactory;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.utils.Entry;
import it.polimi.ingsw.utils.config.Prefs;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Entry<WaitingRoom, Game>> waitingRooms;

    public Controller() {
        this.waitingRooms = new ArrayList<>();
    }

    public Game getGameOf(User user) throws InvalidUserException {
        WaitingRoom room = getWaitingRoomOf (user);
        for (Entry<WaitingRoom, Game> entry : waitingRooms) {
            if (entry.getKey () == room) {
                return entry.getValue ();
            }
        }
        throw new InvalidUserException ();
    }

    public WaitingRoom getWaitingRoomOf(User user) throws InvalidUserException {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms) {
            if (entry.getKey ().contains(user))
                return entry.getKey ();
        }
        throw new InvalidUserException ();
    }

    private void startMatch(User user) throws IllegalNumberOfPlayersException, InvalidUserException, TooManyPlayersException, FileNotFoundException {
        WaitingRoom room = getWaitingRoomOf (user);
        if (room.isFull ()) {
            GameFactory factory = new GameFactory ();
            Game game = factory.MultiOrSingleplayerGame (room.getSize ());
            ArrayList<User> users = room.getAllUsers ();
            for (User userInRoom : users) {
                Player player = game.createPlayer ();
                room.setPlayerOf (userInRoom, player);
                // TODO : game.attach (userInRoom.getView());
            }
            factory.setup (game);
        }
    }

    public synchronized void setWaitingRoomDimension(User user, int newDim) throws InvalidUserException, ImpossibleChangingSizeException, IllegalNumberOfPlayersException, TooManyPlayersException, FileNotFoundException {
        WaitingRoom room = getWaitingRoomOf (user);
        room.setSize (newDim, user);
        if (room.isFull ())
            startMatch (user);
    }

    private void newWaitingRoom() {
        waitingRooms.add (new Entry(new WaitingRoom (Prefs.getMaxUsersInWaitingRoom ()), null));
    }

    private WaitingRoom addToLastWaitingRoom(User user) throws FullWaitingRoomException, InvalidUserException, FirstWaitingRoomException {
        if (!waitingRooms.isEmpty ()) {
            WaitingRoom room = this.waitingRooms.get (waitingRooms.size () - 1).getKey ();
            room.put (user);
            return room;
        }
        throw new FirstWaitingRoomException ();
    }

    public synchronized void register(User user) throws FileNotFoundException, InvalidUserException {
        try {
            WaitingRoom room = addToLastWaitingRoom (user);
            if (room.isFull ())
                startMatch (user);
        } catch (FullWaitingRoomException | TooManyPlayersException | IllegalNumberOfPlayersException | FirstWaitingRoomException e) {
            newWaitingRoom ();
            register (user);
        }
    }

    public void handleCommandOf(User user, Command command) throws Exception {
        synchronized (getGameOf (user)) {
            command.handled (this, user);
        }
    }

    public void handleMatchMoveOf (User user, Action action) throws Exception {
        getGameOf(user).performCommandOf(this.getWaitingRoomOf (user).getPlayerOf (user), action);
    }
}
