package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.commands.Command;
import it.polimi.ingsw.server.controller.exception.*;
import it.polimi.ingsw.server.model.exception.EmptyDeckException;
import it.polimi.ingsw.server.model.exception.IllegalNumberOfPlayersException;
import it.polimi.ingsw.server.model.exception.TooManyPlayersException;
import it.polimi.ingsw.server.model.exception.WrongBoardException;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.GameFactory;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gamelogic.actions.Action;
import it.polimi.ingsw.utils.Entry;
import it.polimi.ingsw.utils.config.Prefs;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Entry<WaitingRoom, Game>> waitingRooms;

    public Controller() {
        this.waitingRooms = new ArrayList<>();
    }

    private Game getGameOf(User user) throws InvalidUserException {
        WaitingRoom room = getWaitingRoomOf (user);
        for (Entry<WaitingRoom, Game> entry : waitingRooms) {
            if (entry.getKey () == room) {
                return entry.getValue ();
            }
        }
        throw new InvalidUserException ();
    }

    private WaitingRoom getWaitingRoomOf(User user) throws InvalidUserException {
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms) {
            if (entry.getKey ().contains(user))
                return entry.getKey ();
        }
        throw new InvalidUserException ();
    }


    public synchronized void startMatch(User user) throws IllegalNumberOfPlayersException, InvalidUserException, TooManyPlayersException, FileNotFoundException, EmptyDeckException, OnlyLeaderCanStartGameException, WrongBoardException {
        WaitingRoom room = getWaitingRoomOf (user);
        if (room.getLeader () == user) {
            if (room.isFull ()) {
                GameFactory factory = new GameFactory ();
                Game game = factory.MultiOrSingleplayerGame (room.getSize ());
                addGameToRoom(game, room);
                ArrayList<User> users = room.getAllUsers ();
                for (User userInRoom : users) {
                    Player player = game.createPlayer ();
                    room.setPlayerOf (userInRoom, player);
                    player.attach (userInRoom.getView ());
                    game.attach (userInRoom.getView ());
                    player.setUsername (userInRoom.getUsername ());
                }
                factory.setup (game);
            }
            else
                throw new IllegalNumberOfPlayersException ();
        }
        else
            throw new OnlyLeaderCanStartGameException ();
    }

    private void addGameToRoom(Game game, WaitingRoom room) {
        this.waitingRooms.forEach (entry -> {
            if(entry.getKey ().equals (room))
                entry.setValue (game);
        });
    }

    public synchronized void setWaitingRoomDimension(User user, int newDim) throws InvalidUserException, ImpossibleChangingSizeException {
        WaitingRoom room = getWaitingRoomOf (user);
        room.setSize (newDim, user);
    }

    public synchronized void registerToNewRoom(User user) throws FullWaitingRoomException, InvalidUserException {
        newWaitingRoom ().put (user);
    }

    private WaitingRoom newWaitingRoom() {
        WaitingRoom room = new WaitingRoom (Prefs.getMaxUsersInWaitingRoom ());
        waitingRooms.add (new Entry<> (room, null));
        return room;
    }

    private void addToLastWaitingRoom(User user) throws FullWaitingRoomException, InvalidUserException, FirstWaitingRoomException {
        if (!waitingRooms.isEmpty ()) {
            WaitingRoom room = getFirstNotFullWaitingRoom();
            room.put (user);
        }
        else
            throw new FirstWaitingRoomException ();
    }

    private WaitingRoom getFirstNotFullWaitingRoom() {
        for (Entry<WaitingRoom, Game> waitingRoom : this.waitingRooms) {
            WaitingRoom room = waitingRoom.getKey ();
            if (!room.isFull ())
                return room;
        }
        return this.waitingRooms.get (waitingRooms.size () - 1).getKey ();
    }


    public synchronized void register(User user) throws InvalidUserException {
        try {
            addToLastWaitingRoom (user);
        } catch (FullWaitingRoomException | FirstWaitingRoomException e) {
            newWaitingRoom ();
            register (user);
        }
    }

    public synchronized void handleCommandOf(User user, Command command) throws Exception {
        command.handled (this, user);
    }

    public void handleMatchMoveOf (User user, Action action) throws Exception {
        getGameOf(user).performActionOf (this.getWaitingRoomOf (user).getPlayerOf (user), action);
    }

    public synchronized void disconnect(User user) throws InvalidUserException {
        getWaitingRoomOf (user).disconnect (user);
    }

    public synchronized void registerToWaitingRoomWith(int ID, User user) throws FullWaitingRoomException, InvalidUserException {
        for (WaitingRoom room : allRooms ())
            if (room.getID () == ID)
                room.reconnection (user);
    }

    private ArrayList<WaitingRoom> allRooms() {
        ArrayList<WaitingRoom> rooms = new ArrayList<> ();
        for (Entry<WaitingRoom, Game> entry : this.waitingRooms)
            rooms.add (entry.getKey ());
        return rooms;
    }
}
