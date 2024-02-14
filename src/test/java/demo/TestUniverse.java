package demo;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import kinetics.Location;
import kinetics.Velocity;
import baryModel.simpleObjects.PhysicalBody;
import baryModel.simpleObjects.PhysicalBaryObject;
import baryModel.BaryUniverse;

//
class TestUniverse extends BaryUniverse {
    //
    TestUniverse() {
        super();
        addCommonObjects(2, 6000, new double [] {-1500, -1000, 0}, false);
        addNewSimpleObject(
                7000, -5000, 0,
                400, Math.PI, 0,
                new PhysicalBody("object-1", 10000, 100, Color.CYAN));
        addNewSimpleObject(
                7000, 2000, 0,
                100, Math.PI, 0,
                new PhysicalBody("object-2", 7000, 100, Color.MAGENTA));
    }

    @SuppressWarnings("SameParameterValue")
    private void addNewSimpleObject(double x, double y, double z,
                                    double speed, double horizontalAngle, double verticalAngle,
                                    @NotNull PhysicalBody body) {
        addObject(new PhysicalBaryObject(
                this,
                new Location(x, y, z),
                new Velocity(speed, horizontalAngle, verticalAngle),
                null,
                body));
    }

    //prepares a cluster of objects, arranged as a xyz cube
    @SuppressWarnings("SameParameterValue")
    private void addCommonObjects(int membersPerSide, double separation,
                                  double @NotNull [] clusterLocation, boolean doVertical) {
        double sideLength = (membersPerSide - 1) * separation;
        double @NotNull [] startingLocation = new double [] {
                clusterLocation[0] - sideLength / 2,
                clusterLocation[1] - sideLength / 2,
                clusterLocation[2] - sideLength / 2};
        for (int i = 0; i < membersPerSide; i++) { //checks x-axis
            double x = startingLocation[0] + i * separation;
            for (int j = 0; j < membersPerSide; j++) { //checks y-axis
                double y = startingLocation[1] + j * separation;
                if (doVertical) {
                    for (int k = 0; k < membersPerSide; k++) { //checks z-axis
                        double z = startingLocation[2] + k * separation;
                        @NotNull String nameSuffix = "-[" + i +"," + j + ", " + k + "]";
                        addNewCommonObject(x, y, z, nameSuffix);
                    }
                } else {
                    @NotNull String nameSuffix = "-[" + i +"," + j + "]";
                    addNewCommonObject(x, y, clusterLocation[2], nameSuffix);
                }
            }
        }
    }

    private void addNewCommonObject(double x, double y, double z, @NotNull String nameSuffix) {
        @NotNull String name = "object" + nameSuffix;
        addNewSimpleObject(
                x, y, z,
                10, 0, 0,
                new PhysicalBody(name, 4000, 100, Color.green));
    }
}