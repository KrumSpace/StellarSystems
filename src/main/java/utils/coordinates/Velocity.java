package utils.coordinates;

import org.jetbrains.annotations.NotNull;

import utils.UpdatableValueInterface;

//
public abstract class Velocity implements ConvertibleCoordinateSystemInterface, UpdatableValueInterface.BufferedVelocityInterface {
    //
    protected Velocity() {}

    //
    public static class VelocityCartesian extends Velocity {
        @SuppressWarnings("FieldMayBeFinal")
        private double
                speed,
                direction;

        //
        public VelocityCartesian(double speed, double direction) {
            super();
            this.speed = speed;
            this.direction = direction;
        }

        //
        @Override
        public final double @NotNull [] getCartesian() {
            return new double [] {speed, direction};
        }

        //
        @Override
        public final double @NotNull [] getRadial() {
            //TODO: finish this
            double @NotNull [] cartesianArray = getCartesian();
            return new double [] {
                    0, //angular
                    0 //radial
            };
        }

        //
        @Override
        public final void update() {
            //TODO: currently unused, nothing to update
        }
    }

    //
    public static class VelocityRadial extends Velocity {
        @SuppressWarnings("FieldMayBeFinal")
        private double
                angularVelocity,
                radialVelocity;

        //
        public VelocityRadial(double angularVelocity, double radialVelocity) {
            super();
            this.angularVelocity = angularVelocity;
            this.radialVelocity = radialVelocity;
        }

        //
        @Override
        public final double @NotNull [] getCartesian() {
            //TODO: finish this
            double @NotNull [] polarArray = getRadial();
            return new double [] {
                    0, //speed
                    0 //direction
            };
        }

        //
        @Override
        public final double @NotNull [] getRadial() {
            return new double [] {angularVelocity, radialVelocity};
        }

        //
        @Override
        public final void update() {
            //TODO: currently unused, nothing to update
        }
    }
}