package it.polimi.ingsw.model.personalboard;

import it.polimi.ingsw.exception.*;

import java.util.ArrayList;

/**
 * Class that represents a collection of depots.
 */
public class WarehouseDepots {


    private ArrayList<Depot> listDepot;


    /**
     * Constructor method of this class.
     */
    WarehouseDepots(int capacityOfDepot1, int capacityOfDepot2) {
        //TODO: read from json file: how many depots and their capacity. Then create all the depots and put then in the arraylist.
        listDepot = new ArrayList<>(0);
        Depot temporaryDepot1 = new Depot(capacityOfDepot1);
        listDepot.add(temporaryDepot1);
        Depot temporaryDepot2 = new Depot(capacityOfDepot2);
        listDepot.add(temporaryDepot2);
    }

    //The method above is used temporarily for testing waiting for the parser. The method below will be the real method.

    /*
    WarehouseDepots(){
        //read from Json file: numDepots.
        listDepot = new ArrayList<>(0);
        for(int i = 0; i < numDepots; i++){
            //read from Json file the capacity of the i-th depot: depotCapacity.
            Depot temporaryDepot = new Depot(depotCapaity);
            listDepot.add(temporaryDepot);
        }
    }*/


    /**
     * Method that checks if this warehouse is empty.
     * @return -> boolean: true if all depots are empty, otherwise false.
     */
    boolean ifWarehouseIsEmpty(){
        for(Depot d : listDepot){
            if(! d.ifDepotIsEmpty())
                return false;
        }
        return true;
    }


    /**
     * Method that checks if the provided resource is already contained into another depot.
     * @param resourceToCompare -> resource provided in input.
     * @param depotIndex -> integer that represents the number of the depot in which you want to store the resource.
     * @return -> boolean: true if every other depot contains a different type of resource.
     * @throws Exception -> exception thrown if there is another depot that contains a resource with the same type of the one
     * provided.
     */
    private boolean ifNotSameTypeInOtherDepots(StorableResource resourceToCompare, int depotIndex)
            throws Exception {

        for(Depot d : listDepot){
            if(listDepot.lastIndexOf(d) != depotIndex){
                if(!(d.ifDepotIsEmpty())){
                    if(d.getStoredResource().ifSameResourceType(resourceToCompare)) {
                        throw new SameResourceTypeInDifferentDepotsException();
                    }
                }
            }
        }
        return true;
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

        Depot currentDepot;
        if(ifDepotIndexIsCorrect(depotIndex)){
            if (ifNotSameTypeInOtherDepots(resourceToStore, depotIndex)) {
                currentDepot = listDepot.get(depotIndex);
                currentDepot.storeResourceInDepot(resourceToStore);
            }
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
    StorableResource getResourceFromDepot(int depotIndex) throws NegativeResourceAmountException {

        StorableResource temporaryResource;
        temporaryResource = listDepot.get(depotIndex).getStoredResource();
        if(temporaryResource != null)
            return (StorableResource) temporaryResource.copyResource();
        else
            return null;
    }
}