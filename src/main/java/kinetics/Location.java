package kinetics;

import org.jetbrains.annotations.NotNull;

import coordinates.CartesianCoordinates;
import precalculability.TypedTimedCalculable;
import precalculability.Updatable;

//a cartesian xyz location
public class Location extends CartesianCoordinates implements TypedTimedCalculable<Velocity>, Updatable {
    private double dx, dy, dz;

    //
    public Location(double x, double y, double z) {
        super(x, y, z);
        dx = 0;
        dy = 0;
        dz = 0;
    }

    //
    @Override
    public final void calculate(double time, @NotNull Velocity velocity) {
        double
                magnitude = velocity.getRadius(),
                verticalAngle = velocity.getVerticalAngle(),
                horizontalMagnitude = magnitude * Math.cos(verticalAngle),
                vz = magnitude * Math.sin(verticalAngle),
                horizontalAngle = velocity.getHorizontalAngle(),
                vx = horizontalMagnitude * Math.cos(horizontalAngle),
                vy = horizontalMagnitude * Math.sin(horizontalAngle);
        dx = vx * time;
        dy = vy * time;
        dz = vz * time;
    }

    //
    @Override
    public final void update() {
        increaseCartesian(dx, dy, dz);
    }
}