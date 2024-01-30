package baryModel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import utils.MathUtils;
import utils.coordinates.Location;
import utils.coordinates.Velocity;
import utils.coordinates.Coordinates;
import utils.coordinates.CoordinateContainerInterface;
import utils.UpdatableValueInterface;

//
public abstract class BaryObject implements
        CoordinateContainerInterface,
        UpdatableValueInterface.BufferedValueInterface,
        BaryChildInterface {
    private @NotNull BaryObjectContainerInterface parent;
    private @NotNull Coordinates coordinates;
    private final @NotNull InfluenceRadiusCalculator influenceRadiusCalculator;

    //
    public BaryObject(@NotNull BaryObjectContainerInterface parent,
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
    public final @NotNull BaryObjectContainerInterface getParent() {
        return parent;
    }

    //
    @Override
    public final void setParent(@NotNull BaryObjectContainerInterface parent) {
        this.parent = parent;
    }

    //
    @Override
    public void moveLevelUp() throws RootParentException {
        if (parent instanceof BaryUniverse) {
            throw new RootParentException();
        } else {
            //find new parent
            if (!(parent instanceof BaryChildInterface)) {
                throw new RuntimeException("Parent is not a child, therefore does not not have a parent. Unable to move an object up!");
            } else {
                @NotNull BaryObjectContainerInterface grandparent = ((BaryChildInterface) parent).getParent();

                //remove from old list
                parent.removeObject(this);

                //calculate and set new coordinates
                if (parent instanceof BarySystem) {
                    setNewCoordinatesWhenMovingUp((BarySystem) parent);
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

    private void setNewCoordinatesWhenMovingUp(@NotNull BarySystem parentSystem) {
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

    //
    public abstract double getMass();

    //TODO: needs rework, formula too crude
    public double getInfluenceRadius() {
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
        @NotNull BaryObjectContainerInterface parent = getParent();
        return parent instanceof BaryUniverse || parent.getObjects().size() > 2;
    }

    //
    public abstract @NotNull String getName();

    //for graphical purposes
    public abstract @NotNull Color getColor();
}