package baryModel.systems;

import java.util.List;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.SimplePrinting.printLine;

import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import baryModel.exceptions.*;
import baryModel.basicModels.BasicBaryObject;
import baryModel.basicModels.NonInfluentialObject;
import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;
import baryModel.simpleObjects.PhysicalBaryObject;

//
public class BarySystem extends AbstractBarySystem {
    static final boolean MERGE_ON_TOUCH = false;

    //
    public BarySystem(@NotNull BaryObjectContainerInterface parent,
                      @Nullable Location location,
                      @Nullable Velocity velocity,
                      @NotNull Color color) {
        super(parent, location, velocity, color);
    }

    //
    @Override
    public final double getInfluenceRadius() {
        try {
            return super.getInfluenceRadius();
        } catch (@NotNull TopLevelObjectException e) {
            //probably won't ever happen, but good practice to check anyway
            throw new RuntimeException(e);
        }
    }

    //
    @Override
    public final void calculate(double time) {
        super.calculate(time);
        calculateMembers(time);
    }

    //
    @Override
    public void checkMeaninglessSystems() throws ObjectRemovedException {
        double influenceRadius = getInfluenceRadius();
        @NotNull List<@NotNull BasicBaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) { //checks all members
            @NotNull BasicBaryObject object = objects.get(i);
            if (object instanceof AbstractBarySystem) { //check one level deeper, if it's a system
                ((AbstractBarySystem) object).checkMeaninglessSystems();
            }

            //if body exceeds parent's influence radius, move to upper level
            double distance = object.getLocation().getRadius();
            if (distance > influenceRadius) {
                try {
                    object.exitSystem();
                    i--;
                } catch (TopLevelObjectException ignored) {}
            }
        }
        if (objects.size() < 2) { //if system has less than 2 members
            moveAllMembersUp(); //move all members to upper level
            try {
                getParent().removeObject(this); //dissolve system
            } catch (TopLevelObjectException e) {
                throw new RuntimeException(e);
            }
            throw new ObjectRemovedException();
        }
    }

    private void moveAllMembersUp() {
        @NotNull List<@NotNull BasicBaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) {
            @NotNull BasicBaryObject object = objects.get(i);
            try {
                object.exitSystem();
                i--;
            } catch (TopLevelObjectException ignored) {}
        }
    }

    //forms a new system from two objects, object1 and object2 must be children of the same parent!
    public static void formNewSystem(@NotNull BaryObject object1,
                                     @NotNull BaryObject object2,
                                     @NotNull Color color) throws DifferentParentException {
        try {
            @NotNull BaryObjectContainerInterface parent = object1.getParent();
            if (object2.getParent() != parent) {
                throw new DifferentParentException();
            } else {
                double
                        mass1 = object1.getMass(),
                        mass2 = object2.getMass(),
                        totalMass = mass1 + mass2,
                        massRatio1 = mass1 / totalMass,
                        massRatio2 = mass2 / totalMass;

                //calculate locations
                @NotNull Location
                        initialLocation1 = object1.getLocation(),
                        initialLocation2 = object2.getLocation();
                double
                        dxTotal = initialLocation2.getX() - initialLocation1.getX(),
                        dyTotal = initialLocation2.getY() - initialLocation1.getY(),
                        dzTotal = initialLocation2.getZ() - initialLocation1.getZ(),
                        dx1 = -dxTotal * massRatio2,
                        dy1 = -dyTotal * massRatio2,
                        dz1 = -dzTotal * massRatio2,
                        dx2 = dxTotal * massRatio1,
                        dy2 = dyTotal * massRatio1,
                        dz2 = dzTotal * massRatio1;
                @NotNull Location
                        newSystemLocation = new Location(
                                initialLocation1.getX() - dx1,
                                initialLocation1.getY() - dy1,
                                initialLocation1.getZ() - dz1),
                        newLocation1 = new Location(dx1, dy1, dz1),
                        newLocation2 = new Location(dx2, dy2, dz2);

                //calculate velocities
                @NotNull Velocity newSystemVelocity = getWeightedVelocity(
                        object1.getVelocity(), mass1,
                        object2.getVelocity(), mass2,
                        totalMass);

                //actually make the system
                @NotNull AbstractBarySystem newSystem = new BarySystem(parent, newSystemLocation, newSystemVelocity, color);
                parent.addObject(newSystem);

                //transfer members
                transferObjectPrecalculated(object1, newSystem, newLocation1);
                transferObjectPrecalculated(object2, newSystem, newLocation2);
            }
        } catch (TopLevelObjectException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull Velocity getWeightedVelocity(@NotNull Velocity velocity1, double mass1,
                                                         @NotNull Velocity velocity2, double mass2,
                                                         double totalMass) {
        return Velocity.newFromProjections(
                (velocity1.getX() * mass1 + velocity2.getX() * mass2) / totalMass,
                (velocity1.getY() * mass1 + velocity2.getY() * mass2) / totalMass,
                (velocity1.getZ() * mass1 + velocity2.getZ() * mass2) / totalMass);
    }

    private static void transferObjectPrecalculated(@NotNull BaryObject object,
                                                    @NotNull AbstractBarySystem newSystem,
                                                    @NotNull Location newLocation) throws TopLevelObjectException {
        @NotNull Velocity
                initialVelocity = object.getVelocity(),
                newSystemVelocity = newSystem.getVelocity();
        object.transferPrecalculated(
                object.getParent(), newSystem,
                newLocation,
                Velocity.newFromProjections(
                        initialVelocity.getX() - newSystemVelocity.getX(),
                        initialVelocity.getY() - newSystemVelocity.getY(),
                        initialVelocity.getZ() - newSystemVelocity.getZ()),
                new Acceleration(0, 0, 0));
    }

    //System-NonInfluential interaction
    @Override
    public final void interactWith(@NotNull NonInfluentialObject object)
            throws ObjectRemovedException, NeighborRemovedException {
        //TODO: define Physical-NonInfluential interaction here
    }

    //System-PhysicalObject interaction
    @Override
    public final void interactWith(@NotNull PhysicalBaryObject object)
            throws ObjectRemovedException, NeighborRemovedException {
        double distance = getDistanceTo(object.getLocation()).getRadius();
        if (distance < getInfluenceRadius()) {
            //TODO: neighbor joins this system
            printLine("Object " + object.getName() + " should enter system " + getName());
        } else if (distance < object.getInfluenceRadius() && neighborMergeabiltyCheck()) {
            //forms a new system of this and neighbor
            try {
                formNewSystem(this, object, Color.yellow); //TODO: improve the color
                @NotNull ObjectRemovedException exception = new ObjectRemovedException();
                exception.addSuppressed(new NeighborRemovedException());
                throw exception;
            } catch (DifferentParentException ignored) {}
        }
    }

    //System-System interaction
    @Override
    public final void interactWith(@NotNull AbstractBarySystem object)
            throws ObjectRemovedException, NeighborRemovedException {
        double distance = getDistanceTo(object.getLocation()).getRadius();
        boolean mergeOnTouch = MERGE_ON_TOUCH;
        double influence = 0, neighborInfluence = 0;
        try {
            influence = getInfluenceRadius();
            neighborInfluence = object.getInfluenceRadius();
        } catch (@NotNull TopLevelObjectException e) {
            throw new RuntimeException(e);
        }
        if (distance < influence + neighborInfluence) {
            //two systems touch
            if (mergeOnTouch) {
                //TODO: merge this and neighbor into a new system
                printLine("Systems " + getName() + " and " + object.getName() + " should be merged");
            } else {
                //TODO: check if children of both intersect; goes deeper into cycle, ugh
                printLine("Systems " + getName() + " and " + object.getName() + " overlap, members might intersect");
            }
        }
        if (!mergeOnTouch && neighborMergeabiltyCheck()) {
            boolean
                    withinThis = distance < influence,
                    withinNeighbor = distance < neighborInfluence;
            if (withinThis && withinNeighbor) {
                //both are in each other's influence
                //TODO: merge this and neighbor into a new system
                printLine("Systems " + getName() + " and " + object.getName() + " should be merged");
            } else {
                //only one system is within the other's influence
                if (withinThis) {
                    //TODO: neighbor system joins this system
                    printLine("System " + object.getName() + " should enter system " + getName());
                }
                if (withinNeighbor) {
                    //TODO: this system joins neighbor system
                    printLine("System " + getName() + " should enter system " + object.getName());
                }
            }
        }
    }
}