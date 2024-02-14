package baryModel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.MathUtils;
import utils.Representable;
import kinetics.Location;
import kinetics.Velocity;
import kinetics.Acceleration;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.DifferentParentException;
import baryModel.exceptions.ObjectRemovedException;
import baryModel.exceptions.NeighborRemovedException;
import baryModel.systems.AbstractBarySystem;
import baryModel.systems.BarySystem;

//
public abstract class BaryObject extends InfluentialObject implements BaryChildInterface, Representable {
    private static final double GRAVITATIONAL_CONSTANT = 10000; //6.67 * Math.pow(10, -13);
    private @Nullable BaryObjectContainerInterface parent;

    //
    public BaryObject(@Nullable BaryObjectContainerInterface parent,
                      @Nullable Location location,
                      @Nullable Velocity velocity,
                      @Nullable Acceleration acceleration) {
        super(location, velocity, acceleration);
        this.parent = parent;
    }

    @Override
    public void calculate(double time) {
        getAcceleration().addComponent(getGravitationalAcceleration());
        //add other acceleration components here, such as engines or user input
        super.calculate(time);
    }

    private @NotNull Acceleration getGravitationalAcceleration() {
        double parentMass = 0;
        @NotNull Location parentLocation = new Location(0, 0, 0);
        if (parent instanceof @NotNull AbstractBarySystem parentSystem) {
            parentMass = parentSystem.getMassWithout(this);
            parentLocation = parentSystem.getBaryCenterWithout(this);
        }
        double
                dx = parentLocation.getX() - getLocation().getX(),
                dy = parentLocation.getY() - getLocation().getY(),
                dz = parentLocation.getZ() - getLocation().getZ(),
                distanceSquared = dx * dx + dy * dy + dz * dz;
        return new Acceleration(
                GRAVITATIONAL_CONSTANT * parentMass / distanceSquared,
                MathUtils.getAngle(dx, dy),
                MathUtils.getAngle(Math.hypot(dx, dy), dz));
    }

    //
    public double getInfluenceRadius() throws TopLevelObjectException {
        return super.getInfluenceRadius((InfluentialObject) getParent());
    }

    //
    @Override
    public final @NotNull BaryObjectContainerInterface getParent() throws TopLevelObjectException {
        if (parent == null) {
            throw new TopLevelObjectException();
        } else {
            return parent;
        }
    }

    //setting parent to null is possible, but not recommended
    @Override
    public void setParent(@Nullable BaryObjectContainerInterface parent) throws TopLevelObjectException {
        this.parent = parent;
    }

    //
    @Override
    public void exitSystem() throws TopLevelObjectException {
        if (parent instanceof BaryUniverse) {
            throw new TopLevelObjectException();
        } else {
            //find new parent
            if (!(parent instanceof BaryChildInterface)) {
                throw new RuntimeException("Parent is not a child, therefore does not not have a parent. Unable to move an object up!");
            } else {
                @NotNull BaryObjectContainerInterface grandparent = ((BaryChildInterface) parent).getParent();

                //remove from old list
                parent.removeObject(this);

                //calculate and set new coordinates
                if (parent instanceof AbstractBarySystem) {
                    setNewCoordinatesWhenExiting((AbstractBarySystem) parent);
                } else {
                    throw new RuntimeException("Parent is not a system, unable to get coordinates!");
                }

                //set new parent
                setParent(grandparent);

                //add to new list
                grandparent.addObject(this);
            }
        }
    }

    private void setNewCoordinatesWhenExiting(@NotNull AbstractBarySystem parentSystem) {
        @NotNull Location oldSystemCoordinates = parentSystem.getLocation();
        getLocation().increaseCartesian(
                oldSystemCoordinates.getX(),
                oldSystemCoordinates.getY(),
                oldSystemCoordinates.getZ());
        @NotNull Velocity oldSystemVelocity = parentSystem.getVelocity();
        getVelocity().increaseCartesian(
                oldSystemVelocity.getX(),
                oldSystemVelocity.getY(),
                oldSystemVelocity.getZ());
    }

    //unfinished, TODO: finish
    public void transfer() {
        //remove from previous system
        //add to new system
    }

    //
    @Override
    public void enterNeighboringSystem(@NotNull BarySystem neighbor) throws DifferentParentException, TopLevelObjectException {
        //TODO: finish this
    }

    //transfer this object from one system to another with precalculated kinetic parameters
    public final void transferPrecalculated(@NotNull BaryObjectContainerInterface oldParent,
                                            @NotNull BaryObjectContainerInterface newParent,
                                            @NotNull Location newLocation,
                                            @NotNull Velocity newVelocity,
                                            @NotNull Acceleration newAcceleration) {
        try {
            oldParent.removeObject(this);
            setParent(newParent);
            getLocation().copy(newLocation);
            getVelocity().copy(newVelocity);
            getAcceleration().copy(newAcceleration);
            newParent.addObject(this);
        } catch (@NotNull TopLevelObjectException e) {
            throw new RuntimeException(e);
        }
    }

    //
    public final double getDistanceToNeighbor(@NotNull BaryObject neighbor) {
        @NotNull Location
                location = getLocation(),
                neighborLocation = neighbor.getLocation();
        double
                dx = location.getX() - neighborLocation.getX(),
                dy = location.getY() - neighborLocation.getY(),
                dz = location.getZ() - neighborLocation.getZ();
        return Math.hypot(Math.hypot(dx, dy), dz);
    }

    //checks if parent is either the universe or its child count is greater than 2
    public final boolean neighborMergeabiltyCheck() {
        try {
            @NotNull BaryObjectContainerInterface parent = getParent();
            return parent instanceof BaryUniverse || parent.getObjects().size() > 2;
        } catch (@NotNull TopLevelObjectException ignored) {
            return false;
        }
    }

    //forms a new system from this and a neighbor
    public void formNewSystemWithNeighbor(@NotNull BaryObject neighbor) throws ObjectRemovedException {
        @NotNull Color color = Color.yellow; //TODO: improve the color
        try {
            BarySystem.formNewSystem(this, neighbor, color);
            @NotNull ObjectRemovedException exception = new ObjectRemovedException();
            exception.addSuppressed(new NeighborRemovedException());
            throw exception;
        } catch (@NotNull DifferentParentException ignored) {} //TODO: yo, don't ignore this!
    }
}