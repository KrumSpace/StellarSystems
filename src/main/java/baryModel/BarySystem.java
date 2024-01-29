package baryModel;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.SimplePrinting.printLine;

import utils.MathUtils;
import utils.coordinates.Coordinates;
import utils.coordinates.Location;
import utils.coordinates.Velocity;
import baryModel.simpleObjects.PhysicalBody;
import baryModel.simpleObjects.BarySimpleObject;

//
public class BarySystem extends BaryObject implements BaryObjectContainerInterface {
    private static final boolean MERGE_ON_TOUCH = false;
    private final @NotNull List<@NotNull BaryObject> objects = new ArrayList<>();
    private final @NotNull Color color;

    //
    public BarySystem(@Nullable BaryObjectContainerInterface parent,
                      @NotNull Coordinates coordinates,
                      @NotNull Color color) {
        super(parent, coordinates);
        this.color = color;
    }

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

    //TODO: improve this
    @Override
    public final double getMass() {
        double mass = 0;
        for (BaryObject object : objects) {
            mass += object.getMass();
        }
        return mass;
    }

    //
    @Override
    public final @NotNull Color getColor() {
        return color;
    }

    //
    @Override
    public void precalculate(double time) {
        super.precalculate(time);
        for (@NotNull BaryObject object : objects) {
            object.precalculate(time);
        }
    }

    //
    @Override
    public void update() {
        super.update();
        for (@NotNull BaryObject object : objects) {
            object.update();
        }
    }

    //
    @Override
    public void checkMeaninglessSystems() throws ObjectRemovedException {
        double influenceRadius = getInfluenceRadius();
        for (int i = 0; i < objects.size(); i++) { //checks all members
            BaryObject object = objects.get(i);
            if (object instanceof BarySystem) { //check one level deeper, if it's a system
                ((BarySystem) object).checkMeaninglessSystems();
            }

            //if body exceeds parent's influence radius, move to upper level
            double distance = object.getCoordinates().getLocation().getRadial()[0];
            if (distance > influenceRadius) {
                try {
                    object.moveLevelUp();
                    i--;
                } catch (RootParentException ignored) {}
            }
        }
        if (objects.size() < 2) { //if system has less than 2 members
            moveAllMembersUp(); //move all members to upper level
            getParent().removeObject(this); //dissolve system
            throw new ObjectRemovedException();
        }
    }

    private void moveAllMembersUp() {
        for (int i = 0; i < objects.size(); i++) {
            BaryObject object = objects.get(i);
            try {
                object.moveLevelUp();
                i--;
            } catch (RootParentException ignored) {}
        }
    }

    //
    @Override
    public void checkNeighbor(@NotNull BaryObject neighbor) throws
            UnrecognizedBaryObjectTypeException, ObjectRemovedException, NeighborRemovedException {
        double distance = getDistanceToNeighbor(neighbor);
        if (neighbor instanceof BarySimpleObject) {
            //system - simpleObject case
            @NotNull PhysicalBody neighborBody = ((BarySimpleObject) neighbor).getSimpleBody();
            if (distance < getInfluenceRadius()) {
                //TODO: neighbor joins this system
                printLine("Object " + neighborBody.getName() + " should enter system " + this);
            } else if (distance < neighbor.getInfluenceRadius() && neighborMergeabiltyCheck()) {
                //TODO: form a new system of this and neighbor
                //printLine("A new system should be formed between " + this + " and " + neighborBody.getName());
                try {
                    formNewSystem(this, neighbor, Color.yellow); //TODO: improve the color
                    @NotNull ObjectRemovedException exception = new ObjectRemovedException();
                    exception.addSuppressed(new NeighborRemovedException());
                    throw exception;
                } catch (DifferentParentException ignored) {}
            }
        } else if (neighbor instanceof BarySystem) {
            //system - system case
            boolean mergeOnTouch = MERGE_ON_TOUCH;
            double
                    influence = getInfluenceRadius(),
                    neighborInfluence = neighbor.getInfluenceRadius();
            if (distance < influence + neighborInfluence) {
                //two systems touch
                if (mergeOnTouch) {
                    //TODO: merge this and neighbor into a new system
                    printLine("Systems " + this + " and " + neighbor + " should be merged");
                } else {
                    //TODO: check if children of both intersect; goes deeper into cycle, ugh
                    printLine("Systems " + this + " and " + neighbor + " overlap, members might intersect");
                }
            }
            if (!mergeOnTouch && neighborMergeabiltyCheck()) {
                boolean
                        withinThis = distance < influence,
                        withinNeighbor = distance < neighborInfluence;
                if (withinThis && withinNeighbor) {
                    //both are in each other's influence
                    //TODO: merge this and neighbor into a new system
                    printLine("Systems " + this + " and " + neighbor + " should be merged");
                } else {
                    //only one system is within the other's influence
                    if (withinThis) {
                        //TODO: neighbor system joins this system
                        printLine("System " + neighbor + " should enter system " + this);
                    }
                    if (withinNeighbor) {
                        //TODO: this system joins neighbor system
                        printLine("System " + this + " should enter system " + neighbor);
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
        @NotNull BaryObjectContainerInterface parent = object1.getParent();
        if (object2.getParent() != parent) {
            throw new DifferentParentException();
        } else {
            //calculate locations
            double @NotNull []
                    initialLocation1 = object1.getCoordinates().getLocation().getCartesian(),
                    initialLocation2 = object2.getCoordinates().getLocation().getCartesian();
            double
                    dxTotal = initialLocation2[0] - initialLocation1[0],
                    dyTotal = initialLocation2[1] - initialLocation1[1],
                    mass1 = object1.getMass(),
                    mass2 = object2.getMass(),
                    totalMass = mass1 + mass2,
                    massRatio1 = mass1 / totalMass,
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
                    px = initialVelocityProjections1[0] * mass1 + initialVelocityProjections2[0] * mass2,
                    py = initialVelocityProjections1[1] * mass1 + initialVelocityProjections2[1] * mass2,
                    vxSystemFinal = px / totalMass,
                    vySystemFinal = py / totalMass,
                    vx1final = initialVelocityProjections1[0] - vxSystemFinal,
                    vy1final = initialVelocityProjections1[1] - vySystemFinal,
                    vx2final = initialVelocityProjections2[0] - vxSystemFinal,
                    vy2final = initialVelocityProjections2[1] - vySystemFinal;

            //prepare final coordinates
            @NotNull Coordinates
                    systemCoordinates = getFinalCoordinates(
                            initialLocation1[0] + dx1, initialLocation1[1] + dy1,
                            vxSystemFinal, vySystemFinal),
                    finalCoordinates1 = getFinalCoordinates(-dx1, -dy1, vx1final, vy1final),
                    finalCoordinates2 = getFinalCoordinates(dx2, dy2, vx2final, vy2final);

            //actually make the system and transfer members
            @NotNull BarySystem newSystem = new BarySystem(parent, systemCoordinates, color);
            transferObjectPrecalculated(object1, parent, newSystem, finalCoordinates1);
            transferObjectPrecalculated(object2, parent, newSystem, finalCoordinates2);
            parent.addObject(newSystem);
        }
    }

    //
    public static final class DifferentParentException extends Exception {
        //
        DifferentParentException() {
            super("Different parent exception!");
        }
    }

    private static @NotNull Coordinates getFinalCoordinates(double x, double y, double vx, double vy) {
        return new Coordinates(new Location.LocationCartesian(x, y), getNewVelocityFromProjections(vx, vy));
    }

    private static @NotNull Velocity getNewVelocityFromProjections(double vx, double vy) {
        return new Velocity.VelocityCartesian(
                Math.hypot(vx, vy),
                MathUtils.getAngle(vx, vy));
    }

    private static void transferObjectPrecalculated(@NotNull BaryObject object,
                                   @NotNull BaryObjectContainerInterface oldParent,
                                   @NotNull BaryObjectContainerInterface newParent,
                                   @NotNull Coordinates newCoordinates) {
        oldParent.removeObject(object);
        object.setParent(newParent);
        object.setCoordinates(newCoordinates);
        newParent.addObject(object);
    }
}