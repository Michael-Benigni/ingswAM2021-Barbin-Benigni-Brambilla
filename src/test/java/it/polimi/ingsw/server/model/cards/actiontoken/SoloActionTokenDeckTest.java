package it.polimi.ingsw.server.model.cards.actiontoken;

import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;
import it.polimi.ingsw.server.model.cards.leadercards.LeaderCardsDeckTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for "SoloActionTokenDeck" class.
 */
public class SoloActionTokenDeckTest {

    /**
     * Method used to build a new deck of action tokens used in the singleplayer game.
     * @return a new deck of action tokens.
     */
    public static SoloActionTokenDeck initSoloActionTokenDeck() {
        ArrayList<SoloActionToken> tokens = new ArrayList<>(0);
        SoloActionToken token1 = new SoloActionToken();
        SoloActionToken token2 = new SoloActionToken();
        token1.setDiscard2CardsEffect(CardColour.GREEN);
        token2.setMoveBlackCrossAndReShuffle();
        tokens.add(token1);
        tokens.add(token2);
        SoloActionTokenDeck deck = new SoloActionTokenDeck(tokens);
        return deck;
    }

    /**
     * Test for "drawFirst" method of this class.
     * It tests if the method returns the correct action token and swap the entire array successfully.
     */
    @Test
    void checkDrawFirstIfCorrect() {
        ArrayList<SoloActionToken> listOfTokens = new ArrayList<>(0);
        SoloActionToken firstToken = new SoloActionToken();
        firstToken.setDiscard2CardsEffect(CardColour.BLUE);
        SoloActionToken secondToken = new SoloActionToken();
        secondToken.setDiscard2CardsEffect(CardColour.BLUE);
        listOfTokens.add(firstToken);
        listOfTokens.add(secondToken);
        SoloActionToken token = new SoloActionToken();
        token.setMoveBlackCrossAndReShuffle();
        listOfTokens.add(token);
        token.setDiscard2CardsEffect(CardColour.GREEN);
        listOfTokens.add(token);
        token.setMoveBlackCrossBy2();
        listOfTokens.add(token);
        SoloActionTokenDeck deck = new SoloActionTokenDeck(listOfTokens);
        assertEquals(deck.drawFirst(), firstToken);
        assertEquals(deck.drawFirst(), secondToken);
    }
}
