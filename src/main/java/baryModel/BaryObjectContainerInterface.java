package baryModel;

import java.util.ArrayList;
import java.util.List;

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

    //
    void checkMeaninglessSystems();

    //
    void createNewSystems();
}