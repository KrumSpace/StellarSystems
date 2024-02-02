package baryModel;

import org.jetbrains.annotations.NotNull;

import utils.coordinates.Location;
import utils.coordinates.Velocity;
import utils.coordinates.Coordinates;
import utils.coordinates.CoordinateContainerInterface;
import utils.PrecalculableInterface;

//an object with a mass and coordinates and the related stuff
public abstract class MassiveCoordinatedObject implements
        CoordinateContainerInterface, PrecalculableInterface.BufferedValueInterface {
    private final @NotNull InfluenceRadiusCalculator influenceRadiusCalculator;
    private @NotNull Coordinates coordinates;

    //
    MassiveCoordinatedObject(@NotNull Coordinates coordinates) {
        influenceRadiusCalculator = new InfluenceRadiusCalculator(this);
        this.coordinates = coordinates;
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
    public abstract double getMass();

    //
    final double getInfluenceRadius(@NotNull MassiveCoordinatedObject parent) {
        return influenceRadiusCalculator.getInfluenceRadius(parent);
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
}