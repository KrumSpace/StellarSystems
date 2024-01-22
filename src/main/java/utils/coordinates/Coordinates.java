package utils.coordinates;

import org.jetbrains.annotations.NotNull;

import utils.UpdatableValueInterface;

//
public class Coordinates implements UpdatableValueInterface.BufferedValueInterface {
    private @NotNull Location location;
    private @NotNull Velocity velocity;

    //
    public Coordinates(@NotNull Location location, @NotNull Velocity velocity) {
        this.location = location;
        this.velocity = velocity;
    }

    //
    public final @NotNull Location getLocation() {
        return location;
    }

    //
    public final @NotNull Velocity getVelocity() {
        return velocity;
    }

    //
    public final void setLocation(@NotNull Location location) {
        this.location = location;
    }

    //
    public final void setVelocity(@NotNull Velocity velocity) {
        this.velocity = velocity;
    }

    //
    @Override
    public final void precalculate(double time) {
        location.precalculate(time, velocity);
        //TODO: add acceleration here
        //velocity.precalculate(time, acceleration);
    }

    //
    @Override
    public final void update() {
        location.update();
        velocity.update();
    }
}