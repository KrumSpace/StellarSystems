package kinetics;

import org.jetbrains.annotations.NotNull;

import precalculability.TypedTimedCalculable;

//
class TypedCalculableVector<T extends SimpleVector> extends SimpleVector implements TypedTimedCalculable<T> {
    //
    TypedCalculableVector(double magnitude, double horizontalAngle, double verticalAngle) {
        super(magnitude, horizontalAngle, verticalAngle);
    }

    //
    @Override
    public void calculate(double time, @NotNull T derivative) {
        applyDerivative(time, derivative);
    }
}