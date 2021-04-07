package it.polimi.ingsw.model.config;

//import it.polimi.ingsw.model.cards.developmentcards.CardColour;
//import it.polimi.ingsw.model.cards.developmentcards.CardLevel;
//import it.polimi.ingsw.model.personalboard.ResourceType;
import org.junit.jupiter.api.Test;

class ConfigLoaderWriterTest {
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
        ConfigLoaderWriter.saveAsJsonFile(filePath, card);
        DevelopmentCard cardLoaded = (DevelopmentCard) ConfigLoaderWriter.getJavaObjectFromFile(filePath, DevelopmentCard.class);
        assertTrue(cardLoaded.equals(card));
    }*/

    @Test
    public void getJavaObjectFromMainDBTest() {

    }

    @Test
    void saveAsJsonFile() {

    }
}