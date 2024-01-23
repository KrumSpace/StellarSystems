package baryModel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static consoleUtils.SimplePrinting.printLine;

import utils.MathUtils;
import utils.UpdatableValueInterface;
import utils.coordinates.Location;
import utils.coordinates.Velocity;
import utils.coordinates.Coordinates;

//
public abstract class BaryObject implements UpdatableValueInterface.BufferedValueInterface {
    private static final double MASS_INFLUENCE_RADIUS_COEFFICIENT = 1.5;
    private @Nullable BarySystem parent;
    private @NotNull Coordinates coordinates;

    //
    public BaryObject(@Nullable BarySystem parent, @NotNull Coordinates coordinates) {
        this.parent = parent;
        this.coordinates = coordinates;
    }

    //
    public void setParent(@Nullable BarySystem parent) {
        this.parent = parent;
    }

    //
    public @Nullable BarySystem getParent() {
        return parent;
    }

    public void moveLevelUp() throws NullParentException {
        if (parent == null || parent instanceof BaryUniverse) {
            throw new NullParentException();
        } else {
            parent.removeObject(this);
            double @NotNull []
                    oldCoordinates = getCoordinates().getLocation().getCartesian(),
                    oldVelocity = getCoordinates().getVelocity().getCartesian(),
                    oldVelocityProjections = new double [] {
                            oldVelocity[0] * Math.cos(oldVelocity[1]),
                            oldVelocity[0] * Math.sin(oldVelocity[1])},
                    oldSystemCoordinates = parent.getCoordinates().getLocation().getCartesian(),
                    oldSystemVelocity = parent.getCoordinates().getVelocity().getCartesian(),
                    oldSystemVelocityProjections = new double [] {
                            oldSystemVelocity[0] * Math.cos(oldSystemVelocity[1]),
                            oldSystemVelocity[1] * Math.sin(oldSystemVelocity[1])},
                    newVelocity = new double [] {
                            oldVelocityProjections[0] + oldSystemVelocityProjections[0],
                            oldVelocityProjections[1] + oldSystemVelocityProjections[1]};
            setCoordinates(new Coordinates(
                    new Location.LocationCartesian(
                            oldCoordinates[0] + oldSystemCoordinates[0],
                            oldCoordinates[1] + oldSystemCoordinates[1]),
                    new Velocity.VelocityCartesian(
                            Math.hypot(newVelocity[0], newVelocity[1]),
                            MathUtils.getAngle(newVelocity[0], newVelocity[1]))));
            @Nullable BarySystem grandparent = parent.getParent();
            setParent(grandparent);
            if (grandparent == null) {
                printLine("New root object created, no parent to add this to.");
            } else {
                grandparent.addObject(this);
            }
        }
    }

    public static class NullParentException extends Exception {
        public NullParentException() {
            super("Null parent exception!");
        }
    }

    //
    public @NotNull Coordinates getCoordinates() {
        return coordinates;
    }

    //
    public void setCoordinates(@NotNull Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    //
    public abstract double getMass();

    //TODO: needs rework, formula too crude
    public double getInfluenceRadius() {
        double mass = getMass();
        return mass * MASS_INFLUENCE_RADIUS_COEFFICIENT;
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

    //for graphical purposes
    public abstract @NotNull Color getColor();
}