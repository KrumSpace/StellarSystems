package kinetics;

import org.jetbrains.annotations.NotNull;

import utils.MathUtils;
import coordinates.PolarCoordinates;

//
public class SimpleVector extends PolarCoordinates {
    //
    SimpleVector(double magnitude, double horizontalAngle, double verticalAngle) {
        super(magnitude, horizontalAngle, verticalAngle);
    }

    //applies a derivative to this vector
    public final void applyDerivative(double time, @NotNull SimpleVector derivative) {
        double  //gets initial coordinates
                x0 = getX(),
                y0 = getY(),
                z0 = getZ(),
                //calculates deltas
                dx = derivative.getX() * time,
                dy = derivative.getY() * time,
                dz = derivative.getZ() * time,
                //calculates final coordinates
                xFinal = x0 + dx,
                yFinal = y0 + dy,
                zFinal = z0 + dz,
                xyProjection = Math.hypot(xFinal, yFinal);
        setPolar( //sets new coordinates
                Math.hypot(xyProjection, zFinal),
                MathUtils.getAngle(xFinal, yFinal),
                MathUtils.getAngle(xyProjection, zFinal));
    }
}