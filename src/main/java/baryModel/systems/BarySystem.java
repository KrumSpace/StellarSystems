package baryModel.systems;

import java.util.List;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import static consoleUtils.SimplePrinting.printLine;

import utils.MathUtils;
import utils.coordinates.Coordinates;

import baryModel.exceptions.*;
import baryModel.BaryObject;
import baryModel.BaryObjectContainerInterface;
import baryModel.simpleObjects.BarySimpleObject;

//
public class BarySystem extends AbstractBarySystem {
    //
    public BarySystem(@NotNull BaryObjectContainerInterface parent,
                      @NotNull Coordinates coordinates,
                      @NotNull Color color) {
        super(parent, coordinates, color);
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
    public final void precalculate(double time) {
        super.precalculate(time);
        precalculateMembers(time);
    }

    //
    @Override
    public void checkMeaninglessSystems() throws ObjectRemovedException {
        double influenceRadius = getInfluenceRadius();
        @NotNull List<@NotNull BaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) { //checks all members
            BaryObject object = objects.get(i);
            if (object instanceof AbstractBarySystem) { //check one level deeper, if it's a system
                ((AbstractBarySystem) object).checkMeaninglessSystems();
            }

            //if body exceeds parent's influence radius, move to upper level
            double distance = object.getCoordinates().getLocation().getRadial()[0];
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
        @NotNull List<@NotNull BaryObject> objects = getObjects();
        for (int i = 0; i < objects.size(); i++) {
            BaryObject object = objects.get(i);
            try {
                object.exitSystem();
                i--;
            } catch (TopLevelObjectException ignored) {}
        }
    }

    //
    @Override
    public void checkNeighbor(@NotNull BaryObject neighbor) throws
            UnrecognizedBaryObjectTypeException, ObjectRemovedException, NeighborRemovedException {
        double distance = getDistanceToNeighbor(neighbor);
        if (neighbor instanceof @NotNull BarySimpleObject neighborObject) {
            //system - simpleObject case
            if (distance < getInfluenceRadius()) {
                //TODO: neighbor joins this system
                printLine("Object " + neighborObject.getName() + " should enter system " + getName());
            } else if (distance < neighborObject.getInfluenceRadius() && neighborMergeabiltyCheck()) {
                //TODO: form a new system of this and neighbor
                //printLine("A new system should be formed between " + getName() + " and " + neighbor.getName());
                try {
                    formNewSystem(this, neighborObject, Color.yellow); //TODO: improve the color
                    @NotNull ObjectRemovedException exception = new ObjectRemovedException();
                    exception.addSuppressed(new NeighborRemovedException());
                    throw exception;
                } catch (DifferentParentException ignored) {}
            }
        } else if (neighbor instanceof @NotNull BarySystem neighborSystem) {
            //system - system case
            boolean mergeOnTouch = MERGE_ON_TOUCH;
            double
                    influence = getInfluenceRadius(),
                    neighborInfluence = neighborSystem.getInfluenceRadius();
            if (distance < influence + neighborInfluence) {
                //two systems touch
                if (mergeOnTouch) {
                    //TODO: merge this and neighbor into a new system
                    printLine("Systems " + getName() + " and " + neighborSystem.getName() + " should be merged");
                } else {
                    //TODO: check if children of both intersect; goes deeper into cycle, ugh
                    printLine("Systems " + getName() + " and " + neighborSystem.getName() + " overlap, members might intersect");
                }
            }
            if (!mergeOnTouch && neighborMergeabiltyCheck()) {
                boolean
                        withinThis = distance < influence,
                        withinNeighbor = distance < neighborInfluence;
                if (withinThis && withinNeighbor) {
                    //both are in each other's influence
                    //TODO: merge this and neighbor into a new system
                    printLine("Systems " + getName() + " and " + neighborSystem.getName() + " should be merged");
                } else {
                    //only one system is within the other's influence
                    if (withinThis) {
                        //TODO: neighbor system joins this system
                        printLine("System " + neighborSystem.getName() + " should enter system " + getName());
                    }
                    if (withinNeighbor) {
                        //TODO: this system joins neighbor system
                        printLine("System " + getName() + " should enter system " + neighborSystem.getName());
                    }
                }
            }
        } else {
            throw new UnrecognizedBaryObjectTypeException();
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
                        massRatio1 = mass1 / totalMass;

                //calculate locations
                double @NotNull []
                        initialLocation1 = object1.getCoordinates().getLocation().getCartesian(),
                        initialLocation2 = object2.getCoordinates().getLocation().getCartesian();
                double
                        dxTotal = initialLocation2[0] - initialLocation1[0],
                        dyTotal = initialLocation2[1] - initialLocation1[1],
                        dx1 = dxTotal * (1 - massRatio1),
                        dy1 = dyTotal * (1 - massRatio1),
                        dx2 = dxTotal - dx1,
                        dy2 = dyTotal - dy1;

                //calculate velocities
                double @NotNull []
                        initialVelocity1 = object1.getCoordinates().getVelocity().getCartesian(),
                        initialVelocity2 = object2.getCoordinates().getVelocity().getCartesian(),
                        initialVelocityProjections1 = MathUtils.getProjectionsFromMagnitudeAndAngle(initialVelocity1[0], initialVelocity1[1]),
                        initialVelocityProjections2 = MathUtils.getProjectionsFromMagnitudeAndAngle(initialVelocity2[0], initialVelocity2[1]);
                double
                        vxSystemFinal = (initialVelocityProjections1[0] * mass1 + initialVelocityProjections2[0] * mass2) / totalMass,
                        vySystemFinal = (initialVelocityProjections1[1] * mass1 + initialVelocityProjections2[1] * mass2) / totalMass;

                //actually make the system
                @NotNull Coordinates systemCoordinates = new Coordinates(
                        initialLocation1[0] + dx1, initialLocation1[1] + dy1,
                        vxSystemFinal, vySystemFinal);
                @NotNull AbstractBarySystem newSystem = new BarySystem(parent, systemCoordinates, color);
                parent.addObject(newSystem);

                //transfer members
                double
                        vx1final = initialVelocityProjections1[0] - vxSystemFinal,
                        vy1final = initialVelocityProjections1[1] - vySystemFinal,
                        vx2final = initialVelocityProjections2[0] - vxSystemFinal,
                        vy2final = initialVelocityProjections2[1] - vySystemFinal;
                @NotNull Coordinates
                        finalCoordinates1 = new Coordinates(-dx1, -dy1, vx1final, vy1final),
                        finalCoordinates2 = new Coordinates(dx2, dy2, vx2final, vy2final);
                object1.transferPrecalculated(parent, newSystem, finalCoordinates1);
                object2.transferPrecalculated(parent, newSystem, finalCoordinates2);
            }
        } catch (TopLevelObjectException e) {
            throw new RuntimeException(e);
        }

    }
}