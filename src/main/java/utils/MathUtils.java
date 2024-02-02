package utils;

import org.jetbrains.annotations.NotNull;

//math utilities
public class MathUtils {
    //some x-y to angle calculations
    public static double getAngle(double x, double y) {
        if (x == 0) {
            double straightAngle = Math.PI / 2;
            if (y > 0) { // 90 degrees
                return straightAngle;
            }
            if (y < 0) { // 270 degrees
                return straightAngle * 3;
            }
            return 0; // center, angle undefined, thus 0
        }
        double arcTangent = Math.atan(y / x);
        if (x < 0) { // 2nd and 3rd quadrants
            return arcTangent + Math.PI;
        }
        if (x > 0 && y < 0) { // 4th quadrant
            return arcTangent + Math.PI * 2;
        }
        return arcTangent; // 1st quadrant
    }

    //2D conversion
    public static double @NotNull [] getPolarFromCartesian(double @NotNull [] cartesian) {
        double
                x = cartesian[0],
                y = cartesian[1];
        return getMagnitudeAndAngleFromProjections(x, y);
    }

    //for cartesian velocity conversion from [speed, direction] to [vx, vy]
    public static double @NotNull [] getMagnitudeAndAngleFromProjections(double x, double y) {
        return new double [] {
                Math.hypot(x, y),
                getAngle(x, y)};
    }

    //2D conversion
    public static double @NotNull [] getCartesianFromPolar(double @NotNull [] polar) {
        double
                radius = polar[0],
                angle = polar[1];
        return getProjectionsFromMagnitudeAndAngle(radius, angle);
    }

    //for cartesian velocity conversion from [speed, direction] to [vx, vy]
    public static double @NotNull [] getProjectionsFromMagnitudeAndAngle(double magnitude, double angle) {
        return new double [] {
                magnitude * Math.cos(angle),
                magnitude * Math.sin(angle)};
    }
}