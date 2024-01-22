package baryModel;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import utils.UpdatableValueInterface;
import utils.coordinates.Coordinates;

//
public abstract class BaryObject implements UpdatableValueInterface.BufferedValueInterface {
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

    //
    public void setLocation(@NotNull Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    //
    public @NotNull Coordinates getCoordinates() {
        return coordinates;
    }

    //
    public abstract double getMass();

    //TODO: needs rework, formula too crude
    public double getInfluenceRadius() {
        double mass = getMass(), coefficient = 2;
        return mass * coefficient;
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