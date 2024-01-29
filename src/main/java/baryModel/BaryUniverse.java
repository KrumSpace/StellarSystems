package baryModel;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import utils.UpdatableValueInterface;

//
public class BaryUniverse implements BaryObjectContainerInterface, UpdatableValueInterface.BufferedValueInterface {
    private final @NotNull List<@NotNull BaryObject> objects = new ArrayList<>();

    //
    public BaryUniverse() {}

    //
    @Override
    public final @NotNull List<@NotNull BaryObject> getObjects() {
        return objects;
    }

    //
    @Override
    public final void addObject(@NotNull BaryObject object) {
        objects.add(object);
    }

    //
    @Override
    public final void removeObject(@NotNull BaryObject object) {
        objects.remove(object);
    }

    //does a complete cycle
    public final void completeCycle(double time) {
        precalculate(time);
        update();
        checkMeaninglessSystems();
        checkChildNeighbors();
    }

    //
    @Override
    public final void precalculate(double time) {
        for (@NotNull BaryObject object : getObjects()) {
            object.precalculate(time);
        }
    }

    //
    @Override
    public final void update() {
        for (@NotNull BaryObject object : getObjects()) {
            object.update();
        }
    }

    //
    @Override
    public final void checkMeaninglessSystems() {
        @NotNull List<@NotNull BaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) {
            @NotNull BaryObject object = objects.get(i);
            if (object instanceof BaryObjectContainerInterface container) {
                try {
                    container.checkMeaninglessSystems();
                } catch (ObjectRemovedException ignored) {
                    i--;
                }
            }
        }
    }
}