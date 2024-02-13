package kinetics;

import org.jetbrains.annotations.NotNull;

import utils.MathUtils;

//
public class Velocity extends TypedCalculableVector<Acceleration> {
    //
    public Velocity(double magnitude, double horizontalAngle, double verticalAngle) {
        super(magnitude, horizontalAngle, verticalAngle);
    }

    //
    @Override
    public final void calculate(double time, @NotNull Acceleration acceleration) {
        super.calculate(time, acceleration);
    }

    //
    public static @NotNull Velocity newFromProjections(double vx, double vy, double vz) {
        double
                horizontalMagnitude = Math.hypot(vx, vy),
                magnitude = Math.hypot(horizontalMagnitude, vz),
                horizontalAngle = MathUtils.getAngle(vx, vy),
                verticalAngle = MathUtils.getAngle(horizontalMagnitude, vz);
        return new Velocity(magnitude, horizontalAngle, verticalAngle);
    }
}