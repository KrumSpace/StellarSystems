package baryModel;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import baryModel.exceptions.*;
import baryModel.systems.BarySystem;

//
public interface BaryChildInterface {
    //
    @NotNull BaryObjectContainerInterface getParent();

    //
    void setParent(@NotNull BaryObjectContainerInterface parent);

    //exits from this system into its parent
    void exitSystem() throws TopLevelObjectException;

    //
    default void checkNeighbors() throws ObjectRemovedException {
        @NotNull List<@NotNull BaryObject> neighbors = getParent().getObjects();
        for (int i = 0; i < neighbors.size(); i++) {
            @NotNull BaryObject neighbor = neighbors.get(i);
            if (neighbor != this) {
                try {
                    checkNeighbor(neighbor);
                } catch (UnrecognizedBaryObjectTypeException e) {
                    throw new RuntimeException(e);
                } catch (NeighborRemovedException ignored) {
                    i--;
                }
            }
        }
    }

    //
    void checkNeighbor(@NotNull BaryObject neighbor) throws
            UnrecognizedBaryObjectTypeException, ObjectRemovedException, NeighborRemovedException;

    //enters neighboring system, has to be of the same parent!
    //TODO: finish this
    void enterNeighboringSystem(@NotNull BarySystem neighbor) throws DifferentParentException, TopLevelObjectException;
}