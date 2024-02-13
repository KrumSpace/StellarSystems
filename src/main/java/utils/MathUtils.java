package utils;

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
}