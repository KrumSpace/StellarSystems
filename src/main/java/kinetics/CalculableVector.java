package kinetics;

import precalculability.TimedCalculable;

//
abstract class CalculableVector extends SimpleVector implements TimedCalculable {
    //
    CalculableVector(double magnitude, double horizontalAngle, double verticalAngle) {
        super(magnitude, horizontalAngle, verticalAngle);
    }
}