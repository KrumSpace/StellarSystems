package utils.coordinates;

import org.jetbrains.annotations.NotNull;

import utils.MathUtils;
import utils.UpdatableValueInterface;

//
public abstract class Location implements ConvertibleCoordinateSystemInterface, UpdatableValueInterface.BufferedLocationInterface {
    //
    protected Location() {}

    //
    public static class LocationCartesian extends Location {
        private double
                x, xTemp,
                y, yTemp;

        //
        public LocationCartesian(double x, double y) {
            super();
            this.x = x;
            xTemp = x;
            this.y = y;
            yTemp = y;
        }

        //
        @Override
        public final double @NotNull [] getCartesian() {
            return new double [] {x, y};
        }

        //
        @Override
        public final double @NotNull [] getRadial() {
            return MathUtils.getPolarFromCartesian(getCartesian());
        }

        //
        @Override
        public final void precalculate(double time, @NotNull Velocity velocity) {
            double @NotNull [] velocityInfo = velocity.getCartesian();
            double
                    speed = velocityInfo[0],
                    orientation = velocityInfo[1],
                    vx = speed * Math.cos(orientation),
                    vy = speed * Math.sin(orientation);
            xTemp = x + vx * time;
            yTemp = y + vy * time;
        }

        //
        @Override
        public final void update() {
            x = xTemp;
            y = yTemp;
        }
    }

    //
    public static class LocationPolar extends Location {
        private double
                distance, distanceTemp,
                phaseAngle, phaseAngleTemp;

        //
        public LocationPolar(double distance, double phaseAngle) {
            super();
            this.distance = distance;
            distanceTemp = distance;
            this.phaseAngle = phaseAngle;
            phaseAngleTemp = phaseAngle;
        }

        //
        @Override
        public final double @NotNull [] getCartesian() {
            return MathUtils.getCartesianFromPolar(getRadial());
        }

        //
        @Override
        public final double @NotNull [] getRadial() {
            return new double [] {distance, phaseAngle};
        }

        //
        @Override
        public final void precalculate(double time, @NotNull Velocity velocity) {
            double @NotNull [] velocityInfo = velocity.getRadial();
            double
                    angularVelocity = velocityInfo[0],
                    radialVelocity = velocityInfo[1];
            phaseAngleTemp = phaseAngle + angularVelocity * time;
            distanceTemp = distance + radialVelocity * time;
        }

        //
        @Override
        public final void update() {
            distance = distanceTemp;
            phaseAngle = phaseAngleTemp;
        }
    }
}