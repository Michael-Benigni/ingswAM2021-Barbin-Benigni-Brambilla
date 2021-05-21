package it.polimi.ingsw.server.model.gameresources.stores;

import it.polimi.ingsw.server.model.exception.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that represents a collection of depots.
 */
public class WarehouseDepots  {
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


    /**
     * @return getter of the capacities ArrayList
     */
    private ArrayList<Integer> getCapacities() {
        return capacities;
    }


    /**
     * method used to create the depots
     */
    private void setListDepot() {
        listDepot = new ArrayList<>();
        for (int i = 0; i < numberOfDepots; i++) {
            Depot depot = new Depot(capacities.get(i));
            listDepot.add(depot);
        }
    }

    /**
     * method used to add an extra depot to the list of all depots.
     * this depot is provided by the power of a leader card
     * @param depotCapacity is the capacity of the extra depot
     * @param resourceType is the unique resourceType storable in this depot
     */
    public void addExtraDepot(int depotCapacity, ResourceType resourceType) throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException {
        ExtraDepot extraDepot = new ExtraDepot(depotCapacity, resourceType);
        this.listDepot.add(extraDepot);
    }

    /**
     * Method that checks if the provided resource is already contained into another depot.
     * @param resourceToCompare is the resource provided in input.
     * @param depotIndex is an integer that represents the number of the depot in which you want to store the resource.
     * @return a boolean value: false if every other depot contains a different type of resource.
     */
    private boolean ifAlreadyContainedInOtherDepots(StorableResource resourceToCompare, int depotIndex) {
        for (int i = 0; i < this.numberOfDepots; i++)
            if(i != depotIndex && listDepot.get(i).alreadyContained(resourceToCompare))
                return true;
        return false;
    }

    /**
     * Method that returns if the index is correct.
     * @param depotIndex the integer we want to verify.
     * @return a boolean value: true if the depot index is correct.
     * @throws WrongDepotIndexException thrown if the integer is less than zero or exceeds the number of depots in
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
     * @param resourceToStore resource to be inserted in the right depot.
     * @param depotIndex integer useful to recognize the right depot.
     * @throws Exception thrown by "ifDepotIndexIsCorrect" and "IfNotSameTypeInOtherDepots" methods.
     */
    public void store(StorableResource resourceToStore, int depotIndex) throws NegativeResourceAmountException, NotEqualResourceTypeException, ResourceOverflowInDepotException, WrongDepotIndexException, SameResourceTypeInDifferentDepotsException {
        if (ifDepotIndexIsCorrect(depotIndex)) {
            if (! ifAlreadyContainedInOtherDepots(resourceToStore, depotIndex) || depotIndex > this.numberOfDepots) {
                listDepot.get(depotIndex).storeResourceInDepot(resourceToStore);
            }
            else
                throw new SameResourceTypeInDifferentDepotsException();
        }
        else{
            throw new WrongDepotIndexException();
        }
    }


    /**
     * Method that finds the right depot in the arraylist and removes the provided resource from it.
     * @param resourceToRemove resource to be removed from the right depot.
     * @param depotIndex integer useful to recognize the right depot.
     * @throws Exception can be thrown by "removeResourceFromDepot" method of "Depot" class.
     */
    public void remove(StorableResource resourceToRemove, int depotIndex) throws WrongDepotIndexException, NegativeResourceAmountException, NotEqualResourceTypeException, EmptyDepotException {
        Depot currentDepot;
        if(ifDepotIndexIsCorrect(depotIndex)) {
            currentDepot = listDepot.get(depotIndex);
            currentDepot.removeResourceFromDepot(resourceToRemove);
        }
    }

    /**
     * Method that swaps two depots in the array with each other.
     * @param depotIndex1 index of the first depot
     * @param depotIndex2 index of the second depot.
     */
    public StorableResource swapDepots(int depotIndex1, int depotIndex2) throws WrongDepotIndexException, EmptyDepotException, NegativeResourceAmountException, NotEqualResourceTypeException {
        StorableResource depotOverflow = null;
        if (ifDepotIndexIsCorrect(depotIndex1) && ifDepotIndexIsCorrect(depotIndex2)) {
            Depot depot1 = listDepot.get(depotIndex1);
            StorableResource resource1 = depot1.getStoredResource();
            depot1.removeResourceFromDepot(resource1);
            Depot depot2 = listDepot.get(depotIndex2);
            StorableResource resource2 = listDepot.get(depotIndex2).getStoredResource();
            depot2.removeResourceFromDepot(resource2);
            try {
                depot1.storeResourceInDepot(resource2);
            } catch (ResourceOverflowInDepotException e) {
                depotOverflow = e.getResource();
            }
            try {
                depot2.storeResourceInDepot(resource1);
            } catch (ResourceOverflowInDepotException e) {
                depotOverflow = e.getResource();
            }
        }
        return depotOverflow;
    }

    /**
     * Method that returns a copy of the resource stored in the depot represented by the provided depot index.
     * @param depotIndex integer that points to the right depot.
     * @return a copy of the resource contained inside the specified depot.
     */
    private StorableResource getResourceFromDepot(int depotIndex) {
        try {
            StorableResource temporaryResource = listDepot.get(depotIndex).getStoredResource();
            return (StorableResource) temporaryResource.clone();
        } catch(EmptyDepotException e) {
            return null;
        }
    }

    /**
     * method that provides an ArrayList
     * of all the storable resources
     * contained into the warehouse
     * @return list of StorableResource
     */
    public ArrayList <StorableResource> getAllResources() {
        ArrayList <StorableResource> listOfAllResources = new ArrayList<>(0);
        for(int i = 0; i < this.listDepot.size(); i++) {
            if(this.getResourceFromDepot(i) != null)
                listOfAllResources.add(this.getResourceFromDepot(i));
        }
        return listOfAllResources;
    }

    /**
     * override of the method equals
     * @param o the object we want to compare
     * @return boolean value: true if the object "o" is equal to the caller
     *                           false if the object "o" isn't equal to the caller
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDepots that = (WarehouseDepots) o;
        return Objects.equals(numberOfDepots, that.numberOfDepots)
                && Objects.equals(capacities, that.capacities)
                && Objects.equals(listDepot, that.listDepot);
    }


    /**
     * override of the method hashCode
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(numberOfDepots, capacities, listDepot);
    }
}