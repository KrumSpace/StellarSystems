package baryModel;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.ObjectRemovedException;

//
public interface BaryObjectContainerInterface {
    //
    @NotNull List<@NotNull BaryObject> getObjects();

    //
    void addObject(@NotNull BaryObject object);

    //
    void removeObject(@NotNull BaryObject object);

    //
    default double getMassWithout(@Nullable BaryObject object) {
        double mass = 0;
        for (@NotNull BaryObject object1 : getObjects()) {
            if (object1 != object) {
                mass += object1.getMass();
            }
        }
        return mass;
    }

    //
    default @NotNull Location getBaryCenter() {
        return getBaryCenterWithout(null);
    }

    //
    default @NotNull Location getBaryCenterWithout(@Nullable BaryObject object) {
        double
                totalMass = getMassWithout(object),
                weightedX = 0,
                weightedY = 0,
                weightedZ = 0;
        for (@NotNull BaryObject object1 : getObjects()) {
            if (object1 != object) {
                double mass = object1.getMass();
                weightedX += object1.getLocation().getX() * mass;
                weightedY += object1.getLocation().getY() * mass;
                weightedZ += object1.getLocation().getZ() * mass;
            }
        }
        return new Location(
                weightedX / totalMass,
                weightedY / totalMass,
                weightedZ / totalMass);
    }

    //consolidates systems
    void checkMeaninglessSystems() throws ObjectRemovedException;

    //checks all child relations
    default void checkChildNeighbors() {
        @NotNull List<@NotNull BaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) {
            @NotNull BaryObject object = objects.get(i);
            if (object instanceof BaryObjectContainerInterface) { // goes deeper, if there are more children
                ((BaryObjectContainerInterface) object).checkChildNeighbors();
            }
            try {
                object.checkNeighbors();
            } catch (ObjectRemovedException exception) {
                break; //crude solution; TODO: improve
            } catch (TopLevelObjectException ignored) {} // if it's a child, it can't be root
        }
    }
}