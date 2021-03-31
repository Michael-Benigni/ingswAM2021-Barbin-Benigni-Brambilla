package it.polimi.ingsw.model;

/**
 * enumeration of the levels that the development card can take on
 */
public enum CardLevel {
    ONE(1), TWO(2), THREE(3);

    private final int level;

    /**
     * constructor of the enum class CardLevel
     * @param level -> indicates the level of the development card
     */
    CardLevel(int level) {
        this.level = level;
    }
}
