package it.polimi.ingsw.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests on ResourceType class
 */
public class ResourceTypeTest {


    /**
     * Test on the constructor method
     * Test if the created object is accordant to the provided parameters
     */
    @Test
    void CheckConstructorIfCorrect()
    {
        ResourceType resourceType = ResourceType.STONE;
        assertSame(resourceType, ResourceType.STONE);
        assertNotSame(resourceType, ResourceType.COIN);
        assertNotSame(resourceType, ResourceType.SERVANT);
        assertNotSame(resourceType, ResourceType.SHIELD);
    }

}
