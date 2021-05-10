package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.developmentcards.CardColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for "SoloActionTokenDeck" class.
 */
public class SoloActionTokenDeckTest {

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
