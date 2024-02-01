package baryModel.exceptions;

//
public final class NeighborRemovedException extends Exception {
    //
    public NeighborRemovedException() {
        super("Neighbor removed. Gotta break the loop.");
    }
}