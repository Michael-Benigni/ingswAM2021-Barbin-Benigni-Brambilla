package it.polimi.ingsw.server.model.exception;

public class IllegalNumberOfPlayersException extends Exception {
    public IllegalNumberOfPlayersException() {
        super("Impossible to start the game." +
                " \nThe number of player that joined the party " +
                " \nmust be equal to the setted room size");
    }
}
