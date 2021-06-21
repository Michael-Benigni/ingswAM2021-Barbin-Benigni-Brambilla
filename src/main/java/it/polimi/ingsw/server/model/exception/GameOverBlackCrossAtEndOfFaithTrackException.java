package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameOverBlackCrossAtEndOfFaithTrackException extends GameOverException {
    public GameOverBlackCrossAtEndOfFaithTrackException() {
        super ("The Black Cross has reached the end of the Faith Track before you!");
    }

    @Override
    public void handleEndGame(Game game, ArrayList<Player> players) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.GAME_OVER_UP);
        writer.addProperty ("losersNames", players.stream ().map (Player::getUsername).collect(Collectors.toList()));
        writer.addProperty ("losersVPs", players.stream ().map ((p) -> p.computeAllVP ().getPoints ()).collect(Collectors.toList()));
        ArrayList<String> blackCross = new ArrayList<> ();
        blackCross.add ("BLACK CROSS");
        ArrayList<Integer> blackCrossPoints = new ArrayList<> ();
        blackCrossPoints.add (0);
        writer.addProperty ("winnersNames", blackCross);
        writer.addProperty ("winnersVPs", blackCrossPoints);
        writer.addProperty ("additionalInfo", this.getMessage ());
        players.forEach ((player) -> player.notifyUpdate (writer.write ()));
    }
}
