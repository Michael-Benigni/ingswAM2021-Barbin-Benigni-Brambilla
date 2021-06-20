package it.polimi.ingsw.server.model.gamelogic.actions;

import it.polimi.ingsw.server.model.cards.actiontoken.SoloActionTokenDeck;
import it.polimi.ingsw.server.model.cards.developmentcards.DevelopmentCardsGrid;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeck;
import it.polimi.ingsw.server.model.gamelogic.Player;
import it.polimi.ingsw.server.model.gameresources.faithtrack.SoloPlayerFaithTrack;
import it.polimi.ingsw.server.model.gameresources.markettray.MarketTray;
import it.polimi.ingsw.utils.Observer;

import java.util.List;

public class SoloPlayerGameBoard extends GameBoard {

    private SoloActionTokenDeck actionTokenDeck;

    /**
     * Constructor method of this class.
     * @param faithTrack
     * @param developmentCardGrid
     * @param marketTray
     * @param leaderCardsDeck
     * @param actionTokenDeck
     */
    public SoloPlayerGameBoard(SoloPlayerFaithTrack faithTrack, DevelopmentCardsGrid developmentCardGrid, MarketTray marketTray, LeaderCardsDeck leaderCardsDeck, SoloActionTokenDeck actionTokenDeck) {
        super(faithTrack, developmentCardGrid, marketTray, leaderCardsDeck);
        this.actionTokenDeck = actionTokenDeck;
    }

    /**
     * Getter method of "actionTokenDeck" of this class.
     * @return
     */
    public SoloActionTokenDeck getActionTokenDeck() {
        return actionTokenDeck;
    }

    @Override
    public SoloPlayerFaithTrack getFaithTrack() {
        return (SoloPlayerFaithTrack) super.getFaithTrack();
    }

    @Override
    public void detachFromAllComponents(Player player) {
        this.getActionTokenDeck ().detachAll (player.getObservers ());
        super.detachFromAllComponents (player);
    }

    @Override
    public void attachToAllComponents(Player player) {
        this.getActionTokenDeck ().attachAll (player.getObservers ());
        super.attachToAllComponents (player);
    }
}
