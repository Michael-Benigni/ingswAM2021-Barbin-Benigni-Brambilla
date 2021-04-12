package it.polimi.ingsw.model.gameresources.stores;

import it.polimi.ingsw.exception.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents a collection of depots.
 */
public class WarehouseDepots {
    private Integer numberOfDepots;
    private ArrayList<Integer> capacities;
    private ArrayList<Depot> listDepot;


    /**
     * Constructor method of this class. It reads from the database how many depots are contained and the capacity of each one.
     */
    public WarehouseDepots(int numberOfDepots, ArrayList<Integer> capacities) {
        this.numberOfDepots = numberOfDepots;
        this.capacities = capacities;
        setListDepot();
    }

    private void setListDepot() {
        listDepot = new ArrayList<>();
        for (int i = 0; i < numberOfDepots; i++) {
            Depot depot = new Depot(capacities.get(i));
            listDepot.add(depot);
        }
    }


    /**
     * Method that checks if the provided resource is already contained into another depot.
     * @param resourceToCompare -> resource provided in input.
     * @param depotIndex -> integer that represents the number of the depot in which you want to store the resource.
     * @return -> boolean: true if every other depot contains a different type of resource.
     * @throws Exception -> exception thrown if there is another depot that contains a resource with the same type of the one
     * provided.
     */
    private boolean ifAlreadyContainedInOtherDepots(StorableResource resourceToCompare, int depotIndex) {
        boolean isContainedInWarehouse = false;
        for (int i = 0; i < listDepot.size(); i++)
            if(i != depotIndex)
                isContainedInWarehouse = isContainedInWarehouse & listDepot.get(i).alreadyContained(resourceToCompare);
        return isContainedInWarehouse;
    }



    /**
     * Method that returns if the index is correct.
     * @param depotIndex -> integer to be verified.
     * @return -> boolean: true if the depot index is correct.
     * @throws WrongDepotIndexException -> thrown if the integer is less than zero or exceeds the number of depots in
     * this warehouse.
     */
    private boolean ifDepotIndexIsCorrect(int depotIndex) throws WrongDepotIndexException {
        if(depotIndex < 0 || depotIndex > listDepot.size())
            throw new WrongDepotIndexException();
        else
            return true;
    }

    /**
     * Method that finds the right depot in the arraylist and puts the provided resource into it.
     * @param resourceToStore -> resource to be inserted in the right depot.
     * @param depotIndex -> integer useful to recognize the right depot.
     * @throws Exception -> thrown by "ifDepotIndexIsCorrect" and "IfNotSameTypeInOtherDepots" methods.
     */
    void storeResourceInWarehouse(StorableResource resourceToStore, int depotIndex) throws Exception {
        if (ifDepotIndexIsCorrect(depotIndex)) {
            if (ifAlreadyContainedInOtherDepots(resourceToStore, depotIndex)) {
                listDepot.get(depotIndex).storeResourceInDepot(resourceToStore);
                return;
            }
            throw new WrongDepotIndexException();
        }
    }


    /**
     * Method that finds the right depot in the arraylist and removes the provided resource from it.
     * @param resourceToRemove -> resource to be removed from the right depot.
     * @param depotIndex -> integer useful to recognize the right depot.
     * @throws Exception -> can be thrown by "removeResourceFromDepot" method of "Depot" class.
     */
    void removeResourceFromWarehouse(StorableResource resourceToRemove, int depotIndex) throws Exception {
        Depot currentDepot;
        if(ifDepotIndexIsCorrect(depotIndex)) {
            currentDepot = listDepot.get(depotIndex);
            currentDepot.removeResourceFromDepot(resourceToRemove);
        }
    }


    /**
     * Method that swaps two depots in the array with each other.
     * @param depotIndex1 -> index of the first depot
     * @param depotIndex2 -> index of the second depot.
     * @throws WrongDepotIndexException -> exception thrown if one of the provided integers is less than 1.
     */
    void swapDepot(int depotIndex1, int depotIndex2) throws WrongDepotIndexException {
        Depot temporaryDepot;
        if(ifDepotIndexIsCorrect(depotIndex1) && ifDepotIndexIsCorrect(depotIndex2))
        {
            temporaryDepot = listDepot.get(depotIndex1);
            listDepot.set(depotIndex1, listDepot.get(depotIndex2));
            listDepot.set(depotIndex2, temporaryDepot);
        }
    }

    /**
     * Method that returns a copy of the resource stored in the depot represented by the provided depot index.
     * @param depotIndex -> integer that points to the right depot.
     * @return -> a copy of the resource contained inside the specified depot.
     * @throws NegativeResourceAmountException -> can be thrown by "copyStorableResource" method of "StorableResource" class.
     */
    private StorableResource getResourceFromDepot(int depotIndex) throws CloneNotSupportedException {
        StorableResource temporaryResource;
        temporaryResource = listDepot.get(depotIndex).getStoredResource();
        if(temporaryResource != null)
            return (StorableResource) temporaryResource.clone();
        else
            return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDepots that = (WarehouseDepots) o;
        return Objects.equals(numberOfDepots, that.numberOfDepots)
                && Objects.equals(capacities, that.capacities)
                && Objects.equals(listDepot, that.listDepot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfDepots, capacities, listDepot);
    }
}