package baryModel;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import baryModel.exceptions.NeighborRemovedException;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.UnrecognizedBaryObjectTypeException;
import baryModel.basicModels.BasicBaryObject;
import baryModel.basicModels.NonInfluentialObject;
import baryModel.simpleObjects.PhysicalBaryObject;
import baryModel.systems.AbstractBarySystem;

//
public interface InteractiveObjectInterface {
    @NotNull Map<@NotNull Class<? extends @NotNull BasicBaryObject>, @NotNull TypedInteractionInterface>
            TYPE_MAP = new HashMap<>() {{
        put(PhysicalBaryObject.class, (object, neighbor) ->
                object.interactWith((PhysicalBaryObject) neighbor));
        put(AbstractBarySystem.class, (object, neighbor) ->
                object.interactWith((AbstractBarySystem) neighbor));
        put(NonInfluentialObject.class, (object, neighbor) ->
                object.interactWith((NonInfluentialObject) neighbor));
    }};

    //
    void interactWith(@NotNull NonInfluentialObject object) throws ObjectRemovedException, NeighborRemovedException;

    //
    void interactWith(@NotNull PhysicalBaryObject object) throws ObjectRemovedException, NeighborRemovedException;

    //
    void interactWith(@NotNull AbstractBarySystem object) throws ObjectRemovedException, NeighborRemovedException;

    //
    void checkNeighbors() throws TopLevelObjectException, ObjectRemovedException;

    //
    default void checkNeighbors(@NotNull List<@NotNull BasicBaryObject> neighbors)
            throws ObjectRemovedException {
        for (int i = 0; i < neighbors.size(); i++) {
            @NotNull BasicBaryObject neighbor = neighbors.get(i);
            if (neighbor != this) {
                @NotNull Class<? extends @NotNull BasicBaryObject> neighborClass = neighbor.getClass();
                @Nullable TypedInteractionInterface interactionInterface = getInteractionForType(neighborClass);
                if (interactionInterface == null) {
                    //object not recognized or interaction not defined
                    //TODO: needs better solution
                    throw new RuntimeException(new UnrecognizedBaryObjectTypeException());
                } else  {
                    try {
                        interactionInterface.accept(this, neighbor);
                    } catch (@NotNull NeighborRemovedException ignored) {
                        i--;
                    }
                }
            }
        }
    }

    //
    @SuppressWarnings("unchecked")
    default @Nullable TypedInteractionInterface getInteractionForType(@NotNull Class<? extends @NotNull BasicBaryObject> type) {
        while (type != null) {
            @Nullable TypedInteractionInterface interaction = TYPE_MAP.get(type);
            if (interaction != null) {
                return interaction;
            }
            type = (Class<? extends BasicBaryObject>) type.getSuperclass(); // Move up the class hierarchy
        }
        return null; // No matching interaction found
    }

    //
    @FunctionalInterface
    interface TypedInteractionInterface {
        //
        void accept(@NotNull InteractiveObjectInterface object, @NotNull BasicBaryObject neighbor)
                throws ObjectRemovedException, NeighborRemovedException;
    }
}