package utils.coordinates;

import org.jetbrains.annotations.NotNull;

import utils.UpdatableValueInterface;

//
public final class Coordinates implements UpdatableValueInterface.BufferedValueInterface {
    private @NotNull Location location;
    private @NotNull Velocity velocity;

    //
    public Coordinates(@NotNull Location location, @NotNull Velocity velocity) {
        this.location = location;
        this.velocity = velocity;
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