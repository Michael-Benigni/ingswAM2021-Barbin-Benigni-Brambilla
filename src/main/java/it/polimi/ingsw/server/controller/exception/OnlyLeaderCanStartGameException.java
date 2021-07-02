package it.polimi.ingsw.server.controller.exception;

public class OnlyLeaderCanStartGameException extends Exception {

    public OnlyLeaderCanStartGameException() {
        super("Impossible to start the game. You aren't the leader of this match!");
    }
}
