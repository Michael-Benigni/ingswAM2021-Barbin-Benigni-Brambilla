package it.polimi.ingsw.server.model.exception;

import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;
import it.polimi.ingsw.server.model.gamelogic.Game;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.utils.network.Header;
import it.polimi.ingsw.utils.network.MessageWriter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameOverByCardsGridException extends GameOverException {
    public GameOverByCardsGridException(CardColour colour) {
        super("This game is over! All " + colour.toString() + " cards of the grid are discarded.");
    }

    @Override
    public void handleEndGame(Game game, ArrayList<Player> players) {
        MessageWriter writer = new MessageWriter ();
        writer.setHeader (Header.ToClient.GAME_OVER_UP);
        writer.addProperty ("losersNames", players.stream ().map (Player::getPosition).collect(Collectors.toList()));
        writer.addProperty ("losersVPs", players.stream ().map ((p) -> p.computeAllVP ().getPoints ()).collect(Collectors.toList()));
        writer.addProperty ("additionalInfo", this.getMessage ());
        ArrayList<String> blackCross = new ArrayList<> ();
        blackCross.add ("BLACK CROSS");
        ArrayList<Integer> blackCrossPoints = new ArrayList<> ();
        blackCrossPoints.add (0);
        writer.addProperty ("winnersNames", blackCross);
        writer.addProperty ("winnersVPs", blackCrossPoints);
        players.forEach ((player) -> player.notifyUpdate (writer.write ()));
    }
}
