package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.AlreadyAddedModifier;
import it.polimi.ingsw.exception.NoEmptyResourceException;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import java.util.ArrayList;
import java.util.HashMap;

public class TemporaryContainer extends UnboundedResourcesContainer {
    private ArrayList <EmptyResource> emptyResources;
    private HashMap<Player, ArrayList<StorableResource>> modifiers;

    @Override
    protected void clear() {
        super.clear();
        emptyResources.clear();
    }

    TemporaryContainer() {
        super();
        this.emptyResources = new ArrayList<>();
        this.modifiers = new HashMap<>();
    }

    public FaithPoint discardAll() {
        ArrayList<StorableResource> resources = null;
        try {
            resources = getAllResources();
        } catch (CloneNotSupportedException e) {
        }
        int resourceCount = 0;
        for (StorableResource r : resources) {
            int amount = 1;
            while (r.amountLessEqualThan(amount))
                amount++;
            resourceCount += amount - 1;
        }
        for (EmptyResource r : emptyResources) {
            resourceCount++;
        }
        clear();
        return new FaithPoint(resourceCount);
    }

    void store (EmptyResource emptyResource) {
        this.emptyResources.add(emptyResource);
    }

    /**
     * Precondition: the input resource must have amount == 1
     * @param resource
     * @throws NoEmptyResourceException
     */
    public void transformEmptyResources(Player player, StorableResource resource) throws NoEmptyResourceException {
        if(this.modifiers.get(player).contains(resource)) {
            try {
                this.emptyResources.remove(emptyResources.size() - 1);
                store(resource);
            } catch (IndexOutOfBoundsException e) {
                throw new NoEmptyResourceException();
            }
        }
    }

    public void addPlayerModifier(Player player, StorableResource resource) throws AlreadyAddedModifier {
        if(this.modifiers.containsKey(player)) {
            if(!this.modifiers.get(player).contains(resource))
                this.modifiers.get(player).add(resource);
            throw new AlreadyAddedModifier();
        }
        else {
            ArrayList<StorableResource> resources = new ArrayList<>();
            resources.add(resource);
            this.modifiers.put(player, resources);
        }
    }
}
