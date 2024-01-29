package baryModel;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

//
public interface BaryObjectContainerInterface {
    @NotNull List<@NotNull BaryObject> objects = new ArrayList<>();

    //
    default @NotNull List<@NotNull BaryObject> getObjects() {
        return objects;
    }

    //
    default void addObject(@NotNull BaryObject object) {
        objects.add(object);
    }

    //
    default void removeObject(@NotNull BaryObject object) {
        objects.remove(object);
    }

    //consolidates systems
    void checkMeaninglessSystems();

    //checks all child relations
    default void checkChildNeighbors() {
        for (int i = 0; i < objects.size(); i++) {
            @NotNull BaryObject object = objects.get(i);
            if (object instanceof BaryObjectContainerInterface) { //goes deeper if there are more children
                ((BaryObjectContainerInterface) object).checkChildNeighbors();
            }
            try {
                object.checkNeighbors();
            } catch (ObjectRemovedException ignored) {
                i--;
            }
        }
    }
}