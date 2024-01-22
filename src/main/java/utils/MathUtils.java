package utils;

import org.jetbrains.annotations.NotNull;

//math utilities
public class MathUtils {
    //some x-y to angle calculations
    public static double getAngle(double x, double y) {
        if (x == 0) {
            double straightAngle = Math.PI / 2;
            if (y > 0) {
                return straightAngle;
            }
            if (y < 0) {
                return straightAngle * 3;
            }
            return 0;
        }
        double arcTangent = Math.atan(y / x);
        if (x < 0) {
            return arcTangent + Math.PI;
        }
        if (x > 0 && y < 0) {
            return arcTangent + Math.PI * 2;
        }
        return arcTangent;
    }

    //2D conversion
    public static double @NotNull [] getPolarFromCartesian(double @NotNull [] cartesian) {
        double
                x = cartesian[0],
                y = cartesian[1];
        return new double [] {
                Math.hypot(x, y),
                getAngle(x, y)};
    }

    //2D conversion
    public static double @NotNull [] getCartesianFromPolar(double @NotNull [] polar) {
        double
                radius = polar[0],
                angle = polar[1];
        return new double [] {
                radius * Math.cos(angle),
                radius * Math.sin(angle)};
    }
}