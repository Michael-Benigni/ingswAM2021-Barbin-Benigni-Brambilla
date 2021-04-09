package it.polimi.ingsw.model.gameresources.markettray;

import it.polimi.ingsw.exception.NegativeResourceAmountException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class MarketTrayTest {

    @Test
    public void ConstructorTest() {
        try {
            MarketTray marketTray = new MarketTray();
        } catch (NegativeResourceAmountException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}