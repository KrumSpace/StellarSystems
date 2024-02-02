package baryModel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.MathUtils;
import utils.coordinates.Location;
import utils.coordinates.Velocity;
import utils.coordinates.Coordinates;
import utils.coordinates.CoordinateContainerInterface;
import utils.PrecalculableInterface;
import baryModel.exceptions.TopLevelObjectException;
import baryModel.exceptions.DifferentParentException;
import baryModel.systems.AbstractBarySystem;
import baryModel.systems.BarySystem;

//
public abstract class BaryObject implements
        CoordinateContainerInterface,
        PrecalculableInterface.BufferedValueInterface,
        BaryChildInterface {
    private @Nullable BaryObjectContainerInterface parent;
    private @NotNull Coordinates coordinates;
    private final @NotNull InfluenceRadiusCalculator influenceRadiusCalculator;

    //
    public BaryObject(@Nullable BaryObjectContainerInterface parent,
                      @NotNull Coordinates coordinates) {
        this.parent = parent;
        this.coordinates = coordinates;
        influenceRadiusCalculator = new InfluenceRadiusCalculator(this);
    }

    //
    @Override
    public final @NotNull Coordinates getCoordinates() {
        return coordinates;
    }

    //
    @Override
    public final void setCoordinates(@NotNull Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    //
    @Override
    public final void setCoordinates(@NotNull Location location, @NotNull Velocity velocity) {
        coordinates.setLocation(location);
        coordinates.setVelocity(velocity);
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
    public abstract double getMass();

    //TODO: maybe needs a rework, formula seems too crude
    //throws an exception, if called on the root object
    public double getInfluenceRadius() throws TopLevelObjectException {
        return influenceRadiusCalculator.getInfluenceRadius();
    }

    //
    @Override
    public void precalculate(double time) {
        coordinates.precalculate(time);
    }

    //
    @Override
    public void update() {
        coordinates.update();
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
        double @NotNull []
                oldCoordinates = getCoordinates().getLocation().getCartesian(),
                oldSystemCoordinates = parentSystem.getCoordinates().getLocation().getCartesian();
        @NotNull Location newLocation = new Location.LocationCartesian(
                oldCoordinates[0] + oldSystemCoordinates[0],
                oldCoordinates[1] + oldSystemCoordinates[1]);

        double @NotNull []
                oldVelocity = getCoordinates().getVelocity().getCartesian(),
                oldVelocityProjections = MathUtils.getProjectionsFromMagnitudeAndAngle(oldVelocity[0], oldVelocity[1]),
                oldSystemVelocity = parentSystem.getCoordinates().getVelocity().getCartesian(),
                oldSystemVelocityProjections = MathUtils.getProjectionsFromMagnitudeAndAngle(oldSystemVelocity[0], oldSystemVelocity[1]),
                newVelocityProjections = new double [] {
                        oldVelocityProjections[0] + oldSystemVelocityProjections[0],
                        oldVelocityProjections[1] + oldSystemVelocityProjections[1]};
        @NotNull Velocity newVelocity = new Velocity.VelocityCartesian(
                Math.hypot(newVelocityProjections[0], newVelocityProjections[1]),
                MathUtils.getAngle(newVelocityProjections[0], newVelocityProjections[1]));

        setCoordinates(new Coordinates(newLocation, newVelocity));
    }

    //unfinished, TODO: finish
    public void transfer() {
        //remove from previous system
        //add to new system
    }

    //transfer this object from one system to another with precalculated coordinates
    public final void transferPrecalculated(@NotNull BaryObjectContainerInterface oldParent,
                                            @NotNull BaryObjectContainerInterface newParent,
                                            @NotNull Coordinates newCoordinates) {
        try {
            oldParent.removeObject(this);
            this.setParent(newParent);
            this.setCoordinates(newCoordinates);
            newParent.addObject(this);
        } catch (@NotNull TopLevelObjectException e) {
            throw new RuntimeException(e);
        }
    }

    //
    public final double getDistanceToNeighbor(@NotNull BaryObject neighbor) {
        double @NotNull []
                location = this.getCoordinates().getLocation().getCartesian(),
                neighborLocation = neighbor.getCoordinates().getLocation().getCartesian();
        return Math.hypot(
                location[0] - neighborLocation[0],
                location[1] - neighborLocation[1]);
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

    @Override
    public void enterNeighboringSystem(@NotNull BarySystem neighbor) throws DifferentParentException, TopLevelObjectException {
        //TODO: finish this
    }

    //
    public abstract @NotNull String getName();

    //for graphical purposes
    public abstract @NotNull Color getColor();
}