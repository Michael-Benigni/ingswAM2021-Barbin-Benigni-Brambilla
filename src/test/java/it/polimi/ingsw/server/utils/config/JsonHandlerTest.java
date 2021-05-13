package it.polimi.ingsw.server.utils.config;

//import it.polimi.ingsw.model.cards.developmentcards.CardColour;
//import it.polimi.ingsw.model.cards.developmentcards.CardLevel;
//import it.polimi.ingsw.model.personalboard.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonHandlerTest {

    /*@Test
    public void getJavaObjectFromFileTest() throws NegativeResourceAmountException, IOException {
        StorableResource servant = new StorableResource(ResourceType.SERVANT, 1);
        StorableResource shield = new StorableResource(ResourceType.SHIELD, 1);
        StorableResource coin = new StorableResource(ResourceType.COIN, 1);
        ArrayList <StorableResource> cost = new ArrayList<>(2);
        ArrayList <StorableResource> consumedResources = new ArrayList<>(1);
        ArrayList <Resource> producedResources = new ArrayList<>(2);
        cost.add(servant);
        cost.add(shield);
        consumedResources.add(coin);
        producedResources.add(shield);
        producedResources.add(servant);
        DevelopmentCard card = new DevelopmentCard(CardColour.YELLOW, CardLevel.ONE, cost, consumedResources, producedResources);
        String filePath = "config/getJavaObjectFromFileTest.json";
        JsonHandler.saveAsJsonFile(filePath, card);
        DevelopmentCard cardLoaded = (DevelopmentCard) JsonHandler.getJavaObjectFromFile(filePath, DevelopmentCard.class);
        assertTrue(cardLoaded.equals(card));
    }*/
    @BeforeEach
    void setUp() {
    }

    @Test
    public void getJavaObjectFromMainDBTest() {

    }

    @Test
    void saveAsJsonFile() {

    }

    @Test
    void testSaveAsJsonFile() {
    }

    @Test
    void getAsJavaObjectFromJSON() {
    }

    @Test
    void getAsJavaObjectFromJSONArray() {
    }

    @Test
    void testGetAsJavaObjectFromJSON() {
    }

    @Test
    void testGetAsJavaObjectFromJSONArray() {
    }
}