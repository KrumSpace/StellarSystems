package baryModel;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import baryModel.exceptions.NeighborRemovedException;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.UnrecognizedBaryObjectTypeException;

//
public interface BaryChildInterface {
    //
    @NotNull BaryObjectContainerInterface getParent();

    //
    void setParent(@NotNull BaryObjectContainerInterface parent);

    //
    void moveLevelUp() throws TopLevelObjectException;

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
}