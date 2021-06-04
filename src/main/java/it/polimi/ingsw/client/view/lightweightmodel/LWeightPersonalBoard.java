package it.polimi.ingsw.client.view.lightweightmodel;

import it.polimi.ingsw.client.view.ui.cli.Colour;

import java.util.ArrayList;

public class LWeightPersonalBoard {

    /**
     *
     */
    public static class LWResource {
        private Type type;
        private int amount;
        private LWResource(Type type, int amount) {
            this.type = type;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return amount + " " + type.toString ();
        }
    }

    /**
     *
     */
    private enum Type {
        SERVANT ("SERVANT", Colour.ANSI_PURPLE), STONE ("STONE", Colour.ANSI_PURPLE),
        SHIELD ("SHIELD", Colour.ANSI_BLUE), COIN ("COIN", Colour.ANSI_YELLOW),
        EMPTY ("empty", null);

        private final String type;
        private final Colour colour;

        Type(String type, Colour colour) {
            this.type = type;
            this.colour = colour;
        }

        @Override
        public String toString() {
            return colour.escape () + type + Colour.RESET.escape ();
        }
    }

    public static class LWDepot {
        private LWResource resource;
        private int capacity;
        private Type type;

        public LWDepot(LWResource resource, int capacity, Type type) {
            this.resource = resource;
            this.capacity = capacity;
            this.type = type;
        }

        @Override
        public String toString() {
            String borderChar = "_";
            String border = "";
            // width must be even
            int width = capacity * 4 + 30;
            for (int i = 0; i < width; i++) {
                border = border + borderChar;
            }
            String capacityStr = "capacity: " + capacity + (type != null ? ", type: " + type : "");
            String resourceStr = "content: " + resource.toString ();
            int numSpacePadding = Math.round((width - Math.max (capacityStr.length (), resourceStr.length ())) / 2);
            for (int i = 0; i < numSpacePadding; i++) {
                if (capacityStr.length () < width - 2)
                    capacityStr = " " + capacityStr + " ";
                if (resourceStr.length () < width - 2)
                    resourceStr = " " + resourceStr + " ";
            }
            if (capacityStr.length () % 2 != 0)
                capacityStr = capacityStr + " ";
            if (resourceStr.length () % 2 != 0)
                resourceStr = resourceStr + " ";
            return  border + "\n" +
                    "/" + capacityStr + "\\\n" +
                    "/" + resourceStr + "\\" ;
        }
    }


    private ArrayList<LWDepot> warehouse;
    private ArrayList<LWResource> strongbox;
    private ArrayList<LWResource> temporary;
    private ArrayList<ArrayList<LWDevCard>> slots; // max 3
    private ArrayList<LWLeaderCard> leaderCardsPlayed; // 4 at the beginning, then 2
    private ArrayList<LWLeaderCard> leaderCardsNotPlayed;

    public static void main(String[] args) {
        System.out.println (new LWDepot (new LWResource (Type.COIN, 3), 1, null));
        System.out.println (new LWDepot (new LWResource (Type.COIN, 3), 2, null));
        System.out.println (new LWDepot (new LWResource (Type.COIN, 3), 3, null));
        System.out.println (new LWDepot (new LWResource (Type.COIN, 3), 2, Type.SERVANT));
    }

    public LWeightPersonalBoard() {
        this.warehouse = new ArrayList<> ();
        this.strongbox = new ArrayList<> ();
        this.temporary = new ArrayList<> ();
        initSlots();
        this.leaderCardsPlayed = new ArrayList<> ();
    }

    private void initSlots(){
        this.slots = new ArrayList<> ();
        for(int i = 0; i < 3; i++){
            slots.add(new ArrayList<>());
        }
    }

    public void updateWarehouse(ArrayList<LWDepot> warehouse) {
        this.warehouse = warehouse;
    }

    public void updateStrongbox(ArrayList<LWResource> strongbox) {
        this.strongbox = strongbox;
    }

    void updateTemporaryContainer(ArrayList<LWResource> temporary) {
        this.temporary = temporary;
    }

    public void updateSlots(LWDevCard addedDevCard, int numberOfSlot) {
        slots.get(numberOfSlot).add(addedDevCard);
    }

    public void updateLeaderCards(ArrayList<LWLeaderCard> leaderCardsNotPlayed,
                                  ArrayList<LWLeaderCard> leaderCardsPlayed) {
        this.leaderCardsNotPlayed = leaderCardsNotPlayed;
        this.leaderCardsPlayed = leaderCardsPlayed;
    }
}
