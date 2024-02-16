package baryModel;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kinetics.Location;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.basicModels.BasicBaryObject;
import baryModel.basicModels.InfluentialObject;

//
public interface BaryObjectContainerInterface {
    //
    @NotNull List<@NotNull BasicBaryObject> getObjects();

    //
    void addObject(@NotNull BasicBaryObject object);

    //
    void removeObject(@NotNull BasicBaryObject object);

    //
    default double getMassWithout(@Nullable BasicBaryObject object) {
        double mass = 0;
        for (@NotNull BasicBaryObject object1 : getObjects()) {
            boolean isInfluential = object1 instanceof InfluentialObject;
            if (object1 != object && isInfluential) {
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
    default @NotNull Location getBaryCenterWithout(@Nullable BasicBaryObject object) {
        double
                totalMass = getMassWithout(object),
                weightedX = 0,
                weightedY = 0,
                weightedZ = 0;
        for (@NotNull BasicBaryObject object1 : getObjects()) {
            boolean isInfluential = object1 instanceof InfluentialObject;
            if (object1 != object && isInfluential) {
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
        @NotNull List<@NotNull BasicBaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) {
            @NotNull BasicBaryObject object = objects.get(i);
            if (object instanceof @NotNull BaryObjectContainerInterface containerObject) {
                // goes deeper, if there are more children
                containerObject.checkChildNeighbors();
            }
            try {
                object.checkNeighbors();
            } catch (ObjectRemovedException exception) {
                break; //crude solution; TODO: improve
            } catch (TopLevelObjectException ignored) {} // if it's a child, it can't be root
        }
    }
}