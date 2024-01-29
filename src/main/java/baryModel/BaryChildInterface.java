package baryModel;

import java.util.List;

import org.jetbrains.annotations.NotNull;

//
public interface BaryChildInterface {
    //
    @NotNull BaryObjectContainerInterface getParent();

    //
    void setParent(@NotNull BaryObjectContainerInterface parent);

    //
    void moveLevelUp() throws RootParentException;

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

    //
    class RootParentException extends Exception {
        public RootParentException() {
            super("Parent is a root member, unable to move up!");
        }
    }
}