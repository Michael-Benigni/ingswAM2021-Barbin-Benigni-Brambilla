package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.NoEmptyResourceException;
import it.polimi.ingsw.exception.NotHaveThisEffectException;
import it.polimi.ingsw.model.gamelogic.actions.Player;
import it.polimi.ingsw.model.gameresources.faithtrack.FaithPoint;
import java.util.ArrayList;
import java.util.HashMap;

public class TemporaryContainer extends UnboundedResourcesContainer {
    private ArrayList <EmptyResource> emptyResources;
    private HashMap<Player, ArrayList<StorableResource>> modifiers;

    @Override
    public void clear() {
        super.clear();
        emptyResources.clear();
    }

    public TemporaryContainer() {
        super();
        this.emptyResources = new ArrayList<>();
        this.modifiers = new HashMap<>();
    }

    public FaithPoint getPenalty() {
        ArrayList<StorableResource> resources = null;
        resources = getAllResources();
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
    public void transformEmptyResources(Player player, StorableResource resource) throws NoEmptyResourceException, NotHaveThisEffectException {
        if(this.modifiers.get(player).contains(resource)) {
            try {
                this.emptyResources.remove(emptyResources.size() - 1);
                store(resource);
            } catch (IndexOutOfBoundsException e) {
                throw new NoEmptyResourceException();
            }
        }
        throw new NotHaveThisEffectException();
    }

    public void addPlayerModifier(Player player, StorableResource resource) {
        if(this.modifiers.containsKey(player)) {
                this.modifiers.get(player).add(resource);
        }
        else {
            ArrayList<StorableResource> resources = new ArrayList<>();
            resources.add(resource);
            this.modifiers.put(player, resources);
        }
    }
}
