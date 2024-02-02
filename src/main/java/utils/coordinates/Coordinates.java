package utils.coordinates;

import org.jetbrains.annotations.NotNull;

import utils.PrecalculableInterface;

//
public final class Coordinates implements PrecalculableInterface.BufferedValueInterface {
    private @NotNull Location location;
    private @NotNull Velocity velocity;

    //
    public Coordinates(@NotNull Location location, @NotNull Velocity velocity) {
        this.location = location;
        this.velocity = velocity;
    }

    //cartesian from projections
    public Coordinates(double x, double y, double vx, double vy) {
        this(
                new Location.LocationCartesian(x, y),
                Velocity.VelocityCartesian.newVelocityFromProjections(vx, vy));
    }

    //blank cartesian
    public Coordinates() {
        this(0, 0, 0, 0);
    }

    //
    public @NotNull Location getLocation() {
        return location;
    }

    //
    public @NotNull Velocity getVelocity() {
        return velocity;
    }

    //
    public void setLocation(@NotNull Location location) {
        this.location = location;
    }

    //
    public void setVelocity(@NotNull Velocity velocity) {
        this.velocity = velocity;
    }

    //
    @Override
    public void precalculate(double time) {
        location.precalculate(time, velocity);
        //TODO: add acceleration here
        //velocity.precalculate(time, acceleration);
    }

    //
    @Override
    public void update() {
        location.update();
        velocity.update();
    }
}