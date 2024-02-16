package kinetics;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import coordinates.CartesianCoordinates;
import coordinates.ConvertibleCoordinates;
import precalculability.TimedCalculable;
import precalculability.Updatable;

//TODO: add rotation
public class KineticParameters implements TimedCalculable, Updatable {
    private final @NotNull Location location;
    private final @NotNull Velocity velocity;
    private final @NotNull Acceleration acceleration;

    //
    public KineticParameters(@Nullable Location location, @Nullable Velocity velocity, @Nullable Acceleration acceleration) {
        this.location = Objects.requireNonNullElse(location, new Location(0, 0, 0));
        this.velocity = Objects.requireNonNullElse(velocity, new Velocity(0, 0, 0));
        this.acceleration = Objects.requireNonNullElse(acceleration, new Acceleration(0, 0, 0));
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
    public @NotNull Acceleration getAcceleration() {
        return acceleration;
    }

    //TODO: add rotation
    @Override
    public void calculate(double time) {
        acceleration.calculate(time);
        //TODO: calculate rotational acceleration here
        velocity.calculate(time, acceleration);
        //TODO: calculate rotational velocity here
        location.calculate(time, velocity); //precalculates temp locations
        //TODO: calculate rotation temps here
    }

    //
    @Override
    public void update() {
        location.update(); //updates locations from temp
        //TODO: update rotation here
    }

    //for calculating distances and directions to target; null means {0, 0, 0}
    public final @NotNull ConvertibleCoordinates getDistanceTo(@Nullable ConvertibleCoordinates target) {
        double
                dx = -location.getX(),
                dy = -location.getY(),
                dz = -location.getZ();
        if (target != null) {
            dx += target.getX();
            dy += target.getY();
            dz += target.getZ();
        }
        return new CartesianCoordinates(dx, dy, dz);
    }
}